package de.nicolasschlecker.vv.net;

import de.nicolasschlecker.vv.common.LoggerFactory;
import de.nicolasschlecker.vv.net.interfaces.IMeasurementClientHandler;
import de.nicolasschlecker.vv.net.interfaces.IMeasurementServer;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MeasurementServer implements IMeasurementServer {
    private static final Logger LOGGER = LoggerFactory.getLogger(MeasurementServer.class);


    private final int port;
    private final ExecutorService executorService;
    private final BlockingQueue<String> blockingQueue;

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
        this.blockingQueue = new ArrayBlockingQueue<>(10);
    }

    @Override
    public synchronized void start() {
        try (final var serverSocket = new ServerSocket(port)) {
            LOGGER.info(() -> String.format("Server started on port %s", port));

            executorService.execute(new MeasurementWriter(blockingQueue));

            while (!Thread.currentThread().isInterrupted()) {
                LOGGER.info("Waiting for new client...");
                executorService.execute(handleClient(serverSocket.accept()));
            }
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Server has crashed", e);
        } finally {
            stop();
        }
    }

    private IMeasurementClientHandler handleClient(Socket socket) {
        final var toClient = new ForwarderReceiver(socket);
        return new MeasurementClientHandler(toClient, this, blockingQueue);
    }

    @Override
    public synchronized void stop() {
        LOGGER.info("Going to shutdown server");
        executorService.shutdownNow();
    }
}
