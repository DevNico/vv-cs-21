package de.nicolasschlecker.vv.net;

import de.nicolasschlecker.vv.common.LoggerFactory;

import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MeasurementServer {
    private static final Logger LOGGER = LoggerFactory.getLogger(MeasurementServer.class);

    private final int port;
    private final ExecutorService executorService;

    /**
     * Starts a server with given port and as many available threads as there are processors available on the host machine.
     *
     * @param port to use
     */
    public MeasurementServer(int port) {
        this(port, Runtime.getRuntime().availableProcessors());
    }

    public MeasurementServer(int port, int nThreads) {
        this.port = port;
        this.executorService = Executors.newFixedThreadPool(nThreads);
    }


    public synchronized void start() {
        try (var serverSocket = new ServerSocket(port)) {
            LOGGER.info(() -> String.format("Server started on port %s", port));

            while (!Thread.currentThread().isInterrupted()) {
                var clientSocket = serverSocket.accept();
                executorService.execute(() -> handleClient(clientSocket));
            }
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Server has crashed", e);
        } finally {
            stop();
        }
    }

    private void handleClient(Socket client) {
        try (var fromClient = new Scanner(client.getInputStream()); var toClient = new PrintStream(client.getOutputStream())) {
            while (fromClient.hasNext() && !Thread.currentThread().isInterrupted()) {
                // Communicate with client
            }
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Client disconnected", e);
        }
    }

    public synchronized void stop() {
        if (!executorService.isShutdown()) {
            executorService.shutdownNow();
        }
    }
}
