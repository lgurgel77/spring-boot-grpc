package one.shimori.demo.beans;

import io.grpc.Server;
import io.grpc.ServerBuilder;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import one.shimori.demo.HelloServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

public class GrpcService {
    Logger logger = LoggerFactory.getLogger(GrpcService.class);

    private final Server server;
    private final ExecutorService executorService;

    private final Integer serverPort;

    public GrpcService(Integer serverPort, ExecutorService executorService) {
        this.serverPort = serverPort;
        this.executorService = executorService;

        server = ServerBuilder
                .forPort(serverPort)
                .addService(new HelloServiceImpl())
                .build();
    }

    @PostConstruct
    public void run() {
        logger.info("gRPC Service starting at port %s...".formatted(serverPort));

        executorService.submit(() -> {
            try {
                server.start();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @PreDestroy
    public void destroy() {
        logger.info("hmmmm");

        server.shutdown();
        try {
            server.awaitTermination(5, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
