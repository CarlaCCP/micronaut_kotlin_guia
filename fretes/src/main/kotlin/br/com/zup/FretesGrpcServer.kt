package br.com.zup

import com.google.protobuf.Any
import com.google.rpc.Code
import com.google.rpc.StatusProto
import io.grpc.Status
import io.grpc.StatusRuntimeException
import io.grpc.stub.StreamObserver
import org.slf4j.LoggerFactory
import java.lang.IllegalArgumentException
import java.lang.IllegalStateException
import javax.inject.Singleton
import kotlin.random.Random

@Singleton
class FretesGrpcServer : FretesServiceGrpc.FretesServiceImplBase() {
    private val logger = LoggerFactory.getLogger(this::class.java)

    override fun calculaFrete(request: CalculaFreteRequest?, responseObserver: StreamObserver<CalculaFreteResponse>?) {

        logger.info("Calculando frete para $request")

        val cep = request?.cep
        if(cep == null || cep.isBlank()){

            // exception grpc
            val erro =  StatusRuntimeException(
                Status.INVALID_ARGUMENT
                    .withDescription("Cep deve ser informado"))

            // Para mostrar as mensagem de erro deve ser chamado assim
            // deve ser explicitos as forma de reportar exceptions para o cliente
            responseObserver?.onError(erro)
        }

        if(!cep!!.matches("[0-9]{5}-[0-9]{3}".toRegex())){
            val erro = Status.INVALID_ARGUMENT
                    .withDescription("Cep inválido")
                .augmentDescription("Formato esperado: xxxxx-xxx")
                .asRuntimeException()
            responseObserver?.onError(erro)
        }


        //Simular uma verificacao de segurança
        if(cep.endsWith("333")){
            val statusProto = com.google.rpc.Status.newBuilder()
                .setCode(Code.PERMISSION_DENIED.number)
                .setMessage("Usuario não pode acessar esse recurso")
                .addDetails(Any.pack(ErrorDetails.newBuilder().setCode(401).setMessage("Token inspirado").build()))
                .build()

            val e = io.grpc.protobuf.StatusProto.toStatusRuntimeException(statusProto)
            responseObserver?.onError(e)
        }


        var valor = 0.0
        try{
            valor = Random.nextDouble(0.0, 140.0)
            if(valor > 100.0){
                throw IllegalStateException("Erro inesperado ao executar logica de negócio")
            }
        } catch (e: Exception){
            responseObserver?.onError(
                Status.INTERNAL
                    .withDescription(e.message)
                    .withCause(e) // anexado ao status de erro, mas não enviado ao Client
                    .asRuntimeException())
        }



        val response = CalculaFreteResponse.newBuilder()
            .setCep(request!!.cep)
            .setValor(valor)
            .build()


        logger.info("Frete calculado: $response")

        responseObserver!!.onNext(response)
        responseObserver.onCompleted()
    }
}