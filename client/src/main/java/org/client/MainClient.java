package org.client;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

public class MainClient {

    public static final String SERVER_URL = "localhost:8080";

    public static void main(String[] args) {
        ManagedChannel channel = ManagedChannelBuilder.forTarget(SERVER_URL)
                .usePlaintext()
                .build();
        var client = new MessageClient(channel);
        for (int i = 0; i < 10; i++) {
            client.sendMessage();
        }
    }
}
