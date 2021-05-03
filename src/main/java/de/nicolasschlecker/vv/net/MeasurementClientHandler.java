package de.nicolasschlecker.vv.net;

import de.nicolasschlecker.vv.common.LoggerFactory;
import de.nicolasschlecker.vv.domain.models.Measurement;
import de.nicolasschlecker.vv.domain.models.Message;
import de.nicolasschlecker.vv.net.interfaces.IMeasurementClientHandler;
import de.nicolasschlecker.vv.net.interfaces.IMeasurementServer;

import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MeasurementClientHandler implements IMeasurementClientHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(MeasurementClientHandler.class);

    private final ForwarderReceiver forwarderReceiver;
    private final IMeasurementServer server;
    private final MeasurementState state;

    public MeasurementClientHandler(ForwarderReceiver forwarderReceiver, IMeasurementServer server) {
        this.forwarderReceiver = forwarderReceiver;
        this.server = server;
        this.state = new MeasurementState();
    }

    @Override
    public void run() {
        LOGGER.info(() -> String.format("New client connected on %s", Thread.currentThread().getName()));
        while (!Thread.currentThread().isInterrupted()) {
            try {
                handleMessage(forwarderReceiver.receive().get());
            } catch (ExecutionException | InterruptedException e) {
                LOGGER.log(Level.SEVERE, "Client connection interrupted", e);
                Thread.currentThread().interrupt();
            }
        }
        LOGGER.info(() -> String.format("Client disconnected on %s", Thread.currentThread().getName()));
    }

    private void handleMessage(Message message) {
        try {
            if (message.getType() == Message.Type.TERMINATE_STATION) {
                LOGGER.info("Server termination requested.");
                server.stop();
                return;
            }

            state.transition(message.getType(), (stateFrom, stateTo) -> {
                if (stateTo == MeasurementState.State.WAITING_FOR_ACKNOWLEDGMENT) {
                    forwarderReceiver.forward(Message.STATION_HELLO);
                } else if (stateTo == MeasurementState.State.WAITING_FOR_MEASUREMENT) {
                    if (stateFrom == MeasurementState.State.WAITING_FOR_MEASUREMENT) {
                        final var measurement = Measurement.fromJson(message.getPayload());
                        LOGGER.info(() -> String.format("Measurement received: %s", measurement));
                        // TODO: Save
                    }

                    forwarderReceiver.forward(Message.STATION_READY);
                } else if (stateTo == MeasurementState.State.TERMINATED) {
                    Thread.currentThread().interrupt();
                } else if (stateTo == MeasurementState.State.ERROR) {
                    LOGGER.log(Level.SEVERE, "Invalid operation requested by client");
                }

                if (stateFrom != stateTo) {
                    LOGGER.info(() -> String.format("State changed: %s -> %s", stateFrom, stateTo));
                }

                return true;
            });
        } catch (IllegalArgumentException e) {
            LOGGER.log(Level.SEVERE, "Invalid message type received from client", e);
            Thread.currentThread().interrupt();
        } catch (NullPointerException e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
            Thread.currentThread().interrupt();
        }
    }
}
