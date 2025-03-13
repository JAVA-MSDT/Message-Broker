package org.grpcparent;

import com.message.service.MessageRequest;
import com.message.service.MessageResponse;
import com.message.service.MessageServiceGrpc;
import io.grpc.stub.StreamObserver;

import java.util.logging.Level;
import java.util.logging.Logger;

public class MessageServer extends MessageServiceGrpc.MessageServiceImplBase {

    private static final Logger LOGGER = Logger.getLogger(MessageServer.class.getName());
    private final static String MESSAGE = "Pong";

    @Override
    public void sendMessage(MessageRequest request, StreamObserver<MessageResponse> responseObserver) {
        String message = request.getMessage();
        LOGGER.log(Level.INFO, "Message:: {0} ", message);
        MessageResponse response = MessageResponse.newBuilder()
                .setMessage(MESSAGE)
                .setTimestamp(System.currentTimeMillis())
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
