package br.com.zup.edu

import io.grpc.ManagedChannel
import io.grpc.Status
import io.grpc.StatusRuntimeException
import io.micronaut.context.annotation.Factory
import io.micronaut.grpc.annotation.GrpcChannel
import io.micronaut.grpc.server.GrpcServerChannel
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import javax.inject.Singleton


@MicronautTest(transactional = false)
internal class CarrosEndpointTest(
    val grpcClient : CarrosGrpcServiceGrpc.CarrosGrpcServiceBlockingStub,
    val repository: CarroRepository){

    @Test
    fun `deve adicionar um novo carro`(){

        // Cenario
        repository.deleteAll()

        // Ação
        val request = CarrosRequest.newBuilder()
            .setModelo("Gol")
            .setPlaca("HPX-1234")
            .build()
        val response = grpcClient.adicionar(request)

        //Validacao
        with(response){
            assertNotNull(id)
            assertTrue(repository.existsById(id)) // efeito colateral
        }

    }

    @Test
    fun `não deve adicionar nvo carro quando carro com placa já existente` (){
        //Cenario
        repository.deleteAll()
        repository.save(Carro("Palio", "HPX-1234"))

        //Acao
        val error = assertThrows<StatusRuntimeException>{
            grpcClient.adicionar(CarrosRequest
                .newBuilder()
                .setModelo("Palio")
                .setPlaca("HPX-1234").build())
        }


        //Validacao
        with(error){
            assertEquals(Status.ALREADY_EXISTS.code, status.code)
            assertEquals("Carro com placa existente", status.description)
        }

    }

    @Test
    fun `nao deve adicionar novo carro quando dados de entrada forem invalidos`() {

        //Cenario
        repository.deleteAll() //pode colocar o beforeEach


        //Acao
        val error = assertThrows<StatusRuntimeException>{
            grpcClient.adicionar(CarrosRequest
                .newBuilder()
                .setModelo("")
                .setPlaca("").build())
        }


        //Validacao
        with(error){
            assertEquals(Status.INVALID_ARGUMENT.code, status.code)
            assertEquals("Dados de entrada inválidos", status.description)
        }

    }

    @Factory
    class Clients{

        @Singleton
        fun blockingStub(@GrpcChannel(GrpcServerChannel.NAME) channel: ManagedChannel)
        : CarrosGrpcServiceGrpc.CarrosGrpcServiceBlockingStub{
            return CarrosGrpcServiceGrpc.newBlockingStub(channel)
        }
    }
}
