package de.nicolasschlecker.vv.net;

import de.nicolasschlecker.vv.common.JsonFactory;
import de.nicolasschlecker.vv.common.LoggerFactory;
import de.nicolasschlecker.vv.domain.exceptions.ConnectionException;
import de.nicolasschlecker.vv.domain.models.Message;
import de.nicolasschlecker.vv.net.interfaces.IForwarderReceiver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ForwarderReceiver implements IForwarderReceiver {
    private static final Logger LOGGER = LoggerFactory.getLogger(ForwarderReceiver.class);
    private final PrintStream toServer;
    private final BufferedReader fromServer;
    private final ExecutorService executor = Executors.newSingleThreadExecutor();

    public ForwarderReceiver(Socket peer) {
        try {
            this.toServer = new PrintStream(peer.getOutputStream());
            this.fromServer = new BufferedReader(new InputStreamReader(peer.getInputStream()));
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
            throw new ConnectionException("Could not connect to Server");
        }
    }

    @Override
    public void forward(Message message) {
        LOGGER.info(() -> String.format("Sent: %s", message.toString()));
        toServer.println(message.toJson());
        toServer.flush();

    }

    @Override
    public Future<Message> receive() {
        return executor.submit(() -> {
            try {
                var response = Message.fromJson(fromServer.readLine());
                LOGGER.info(() -> String.format("Received: %s", response));
                return response;
            } catch (IOException e) {
                e.printStackTrace();
                throw e;
            }
        });
    }

    @Override
    public void close() throws IOException {
        fromServer.close();
        toServer.close();
        executor.shutdownNow();
    }
}
