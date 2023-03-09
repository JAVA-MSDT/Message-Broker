package com.grpc.server;

import com.grpc.service.UserServiceImpl;
import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserServer {

    private static final Logger LOGGER = Logger.getLogger(UserServer.class.getName());
    private Server server;

    public static void main(String[] args) {
        UserServer userServer = new UserServer();
        userServer.startServer();
        // userServer.blockUntilShutdown();;
    }

    public void startServer() {
        int port = 8080;
        try {
            server = ServerBuilder.forPort(port)
                    .addService(new UserServiceImpl())
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
