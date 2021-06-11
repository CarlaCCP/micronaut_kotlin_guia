package br.com.zup.edu

import io.grpc.ManagedChannel
import io.micronaut.context.annotation.Factory
import io.micronaut.grpc.annotation.GrpcChannel
import javax.inject.Singleton

@Factory
class GrpcClientFactory {

    @Singleton
    fun fretesClientStub(@GrpcChannel("fretes-rest") channel: ManagedChannel)
            : FretesServiceGrpc.FretesServiceBlockingStub? {

//        val channel: ManagedChannel = ManagedChannelBuilder
//            .forAddress("localhost", 50051)
//            .usePlaintext()
//            .maxRetryAttempts(10)
//            .build()

        return FretesServiceGrpc.newBlockingStub(channel)
    }
}