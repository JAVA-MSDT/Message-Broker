package org.client;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

public class MainClient {

    public static void main(String[] args) {
        ManagedChannel channel = ManagedChannelBuilder.forTarget("localhost:8080")
                .usePlaintext()
                .build();
        var client = new MessageClient(channel);
        for (int i = 0; i < 10; i++) {
            client.sendMessage();
        }
    }
}
