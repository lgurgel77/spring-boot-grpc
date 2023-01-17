package one.shimori.demo.config;

import one.shimori.demo.beans.GrpcService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ExecutorService;

@Configuration
public class GrpcServiceConfig {
    private final ExecutorService executorService;

    @Value("${grpc.server.port}")
    private Integer grpcServerPort;

    public GrpcServiceConfig(ExecutorService executorService) {
        this.executorService = executorService;
    }

    @Bean
    public GrpcService grpcService() {
        return new GrpcService(grpcServerPort, executorService);
    }
}
