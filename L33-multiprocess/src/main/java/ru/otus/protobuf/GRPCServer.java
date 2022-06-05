package ru.otus.protobuf;


//import io.grpc.Server;
//import io.grpc.ServerBuilder;
//import ru.otus.protobuf.service.RealDBService;
//import ru.otus.protobuf.service.RealDBServiceImpl;
//import ru.otus.protobuf.service.RemoteDBServiceImpl;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import ru.otus.protobuf.service.RemoteService;

import java.io.IOException;

public class GRPCServer {

    public static final int SERVER_PORT = 8190;

    public static void main(String[] args) throws IOException, InterruptedException {

        Server server = ServerBuilder.forPort(SERVER_PORT)
                .addService(new RemoteService())
                .build();

        server.start();

        Runtime.getRuntime().addShutdownHook(new Thread(server::shutdown));

        server.awaitTermination();
    }
}
