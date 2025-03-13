package org.grpcparent;

import java.io.IOException;

import io.grpc.Server;
import io.grpc.ServerBuilder;

public class MainServer {

    private static Server server;

    public static void main(String[] args) throws IOException, InterruptedException {
        server = ServerBuilder.forPort(8080)
                .addService(new MessageServer())
                .build();

        server.start();
        server.awaitTermination();
    }
}
