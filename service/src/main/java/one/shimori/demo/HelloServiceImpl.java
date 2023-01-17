package one.shimori.demo;

import io.grpc.stub.StreamObserver;
import org.example.services.hello.GreetingRequest;
import org.example.services.hello.GreetingResponse;
import org.example.services.hello.HelloServiceGrpc;

public class HelloServiceImpl extends HelloServiceGrpc.HelloServiceImplBase {
    @Override
    public void greeting(GreetingRequest request, StreamObserver<GreetingResponse> responseObserver) {
        GreetingResponse resp = GreetingResponse.newBuilder()
                .setMessage("Hello, %s!".formatted(request.getName()))
                .setTimestamp(System.currentTimeMillis())
                .build();

        responseObserver.onNext(resp);
        responseObserver.onCompleted();
    }
}
