package com.avalancher;

import com.avalancher.service.AvalancherService;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Hello world!
 *
 */
public class App 
{
    private static final Logger logger = LoggerFactory.getLogger(App.class);
    private static final int port = 50051;
    public static void main( String[] args ) throws IOException, InterruptedException {
        Server server = ServerBuilder.forPort(port)
                .addService(new AvalancherService())
                .build()
                .start();

        logger.info("Server started, listening on " + port);

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            logger.info("Shutting down gRPC server...");
            server.shutdown();
            logger.info("Server shut down.");
        }));
        server.awaitTermination();
    }
}
