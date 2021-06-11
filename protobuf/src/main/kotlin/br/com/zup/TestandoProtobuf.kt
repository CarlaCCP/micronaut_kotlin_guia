package br.com.zup

import java.io.FileInputStream
import java.io.FileOutputStream
import javax.imageio.stream.FileImageOutputStream

fun main() {
    val request = FuncionarioRequest.newBuilder()
        .setNome("Carla")
        .setCpf("937596")
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

    println(request)

    // Escrevemos em disco o objeto
    request.writeTo(FileOutputStream("funcionario-request.bin"))

    //Lemos o objeto
    val request2 = FuncionarioRequest.newBuilder()
        .mergeFrom(FileInputStream("funcionario-request.bin"))

    request2.setCargo(Cargo.GERENTE)
        .build()
    println(request2)
}