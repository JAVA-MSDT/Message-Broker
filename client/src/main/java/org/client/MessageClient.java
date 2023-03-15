package org.client;

import com.message.service.MessageRequest;
import com.message.service.MessageResponse;
import com.message.service.MessageServiceGrpc;
import io.grpc.Channel;

import java.util.logging.Level;
import java.util.logging.Logger;

public class MessageClient {
    private static final Logger LOGGER = Logger.getLogger(MessageClient.class.getName());
    private final static String MESSAGE = "Ping";

    private final MessageServiceGrpc.MessageServiceBlockingStub stub;

    public MessageClient(Channel channel) {
        this.stub = MessageServiceGrpc.newBlockingStub(channel);
    }

    public void receiveMessage() {
        MessageRequest request = MessageRequest.newBuilder()
                .setMessage(MESSAGE)
                .setTimestamp(System.currentTimeMillis())
                .build();
        MessageResponse response = stub.sendMessage(request);
        LOGGER.log(Level.INFO, "Response Message:: {0}", response.getMessage());
    }

}
