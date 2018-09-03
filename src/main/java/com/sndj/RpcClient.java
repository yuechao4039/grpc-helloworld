package com.sndj;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.examples.helloworldss.GreeterGrpc;
import io.grpc.examples.helloworldss.HelloReply;
import io.grpc.examples.helloworldss.HelloRequest;

import java.util.concurrent.TimeUnit;

/**
 * Created by jiely on 2017/7/15.
 */
public class RpcClient {
    private final ManagedChannel channel;
    private final GreeterGrpc.GreeterBlockingStub blockingStub;


    public RpcClient(String host, int port) {
        channel = ManagedChannelBuilder.forAddress(host, port)
                .usePlaintext(true)
                .build();

        blockingStub = GreeterGrpc.newBlockingStub(channel);
    }


    public void shutdown() throws InterruptedException {
        channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
    }

    public void greet(String name) {
        HelloRequest request = HelloRequest.newBuilder().setName(name).build();
        HelloReply response = blockingStub.sayHello(request);
        System.out.println(response.getMessage());

    }

    public static void main(String[] args) throws InterruptedException {
        RpcClient client = new RpcClient("127.0.0.1", 50051);
        for (int i = 0; i < 5; i++) {
            client.greet("world:" + i);
        }

    }
}