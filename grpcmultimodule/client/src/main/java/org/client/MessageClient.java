package org.client;

import com.message.service.MessageRequest;
import com.message.service.MessageResponse;
import com.message.service.MessageServiceGrpc;
import io.grpc.Channel;

public class MessageClient {

    private final MessageServiceGrpc.MessageServiceBlockingStub stub;

    public MessageClient(Channel channel) {
        this.stub = MessageServiceGrpc.newBlockingStub(channel);
    }

    public void sendMessage() {
        MessageRequest request = MessageRequest.newBuilder()
                .setMessage("Ping")
                .setTimestamp(System.currentTimeMillis())
                .build();
        MessageResponse response = stub.receiveMessage(request);
        System.out.println(response);
    }

}
