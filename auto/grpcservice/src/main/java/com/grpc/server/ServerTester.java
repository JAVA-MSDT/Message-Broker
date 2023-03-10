package com.grpc.server;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.grpc.service.UserServiceImpl;
import io.grpc.Server;
import io.grpc.ServerBuilder;

public class ServerTester {

    private static final Logger LOGGER = Logger.getLogger(ServerTester.class.getName());
    // private Server server;

    private static Server server;

    public static void main(String[] args) throws IOException, InterruptedException {
        server = ServerBuilder.forPort(8080)
                .addService(new UserServiceImpl())
                .build();

        server.start();
        server.awaitTermination();
//        ServerTester userServer = new ServerTester();
//        userServer.startServer();
//        userServer.blockUntilShutdown();;
    }

    public void startServer() {
        int port = 8080;
        try {
            server = ServerBuilder.forPort(port)
                    //.addService(new UserServiceImpl())
                    .build()
                    .start();
            LOGGER.log(Level.INFO, "Server hs started on port....." + port);

           // Runtime.getRuntime().addShutdownHook(new Thread(UserServer.this::stopServer));
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Error Starting Server");
            throw new RuntimeException(e);
        }

    }

    public void stopServer() {
        if (server != null) {
            try {
                server.shutdown().awaitTermination(30, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                LOGGER.log(Level.SEVERE, "Error Stopping Server - stopServer");
                throw new RuntimeException(e);
            }
        }
    }

    public void blockUntilShutdown() {
        if (server != null) {
            try {
                server.shutdown().awaitTermination();
            } catch (InterruptedException e) {
                LOGGER.log(Level.SEVERE, "Error Stopping Server - blockUntilShutdown");
                throw new RuntimeException(e);
            }
        }
    }
}
