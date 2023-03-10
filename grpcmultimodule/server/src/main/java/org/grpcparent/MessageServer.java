package org.grpcparent;

import com.message.service.MessageRequest;
import com.message.service.MessageResponse;
import com.message.service.MessageServiceGrpc;
import io.grpc.stub.StreamObserver;

public class MessageServer extends MessageServiceGrpc.MessageServiceImplBase {

    @Override
    public void receiveMessage(MessageRequest request, StreamObserver<MessageResponse> responseObserver) {
        String message = request.getMessage();
        System.out.println("Message:: " + message);
        MessageResponse response = MessageResponse.newBuilder()
                .setMessage("Pong")
                .setTimestamp(System.currentTimeMillis())
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
