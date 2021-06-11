package br.com.zup

import io.grpc.ManagedChannel
import io.grpc.ManagedChannelBuilder

fun main() {
    val channel = ManagedChannelBuilder
            .forAddress("localhost", 50051)
        .usePlaintext()
        .build()

    val client = FuncionarioServiceGrpc.newBlockingStub(channel)
    val request = FuncionarioRequest.newBuilder()
        .setNome("Carla")
        .setCpf("937596")
        .setIdade(23)
        .setSalario(2000.20)
        .setAtivo(true)
        .setCargo(Cargo.QA)
        .addEndereco(
            FuncionarioRequest.Endereco.newBuilder()
                .setLogradouro("R tal")
                .setCep("07130230")
                .setComplemento("Casa tal")
                .build())
        .build()

    val response = client.cadastrar(request)
    println(response)
}