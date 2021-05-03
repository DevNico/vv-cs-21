package de.nicolasschlecker.vv;

import de.nicolasschlecker.vv.common.LoggerFactory;
import de.nicolasschlecker.vv.common.config.ApplicationConfig;
import de.nicolasschlecker.vv.domain.models.Measurement;
import de.nicolasschlecker.vv.domain.models.Message;
import de.nicolasschlecker.vv.net.ForwarderReceiver;

import java.io.Closeable;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.time.LocalDateTime;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DemoSensor implements Closeable {
    private static final Logger LOGGER = LoggerFactory.getLogger(DemoSensor.class);

    private final ForwarderReceiver server;

    public DemoSensor(ForwarderReceiver toServer) {
        this.server = toServer;
    }

    @Override
    public void close() throws IOException {
        server.close();
    }


    private Message callServer(Message message) throws InterruptedException, ExecutionException {
        server.forward(message);
        return server.receive().get();
    }


    public static void main(String[] args) {
        final var config = ApplicationConfig.getConfig();

        try (final var sensor = new DemoSensor(new ForwarderReceiver(new Socket(InetAddress.getLocalHost(), config.getPort())))) {
            var response = sensor.callServer(new Message(Message.Type.SENSOR_HELLO));
            if (response.getType() != Message.Type.STATION_HELLO) System.exit(1);

            response = sensor.callServer(new Message(Message.Type.ACKNOWLEDGE));
            if (response.getType() != Message.Type.STATION_READY) System.exit(1);

            final var rand = new Random();
            for (var i = 0; i < 10; i++) {
                final var measurement = new Measurement(Measurement.Type.TEMPERATURE, rand.nextInt() * 100, Measurement.Unit.KELVIN, LocalDateTime.now());
                final var message = new Message(Message.Type.MEASUREMENT, measurement.toJson());
                sensor.callServer(message);
                TimeUnit.SECONDS.sleep(5);
            }
            final var message = new Message(Message.Type.TERMINATE_STATION);
            sensor.callServer(message);
        } catch (ExecutionException | IOException e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
        } catch (InterruptedException e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
            Thread.currentThread().interrupt();
        }
    }
}
