package br.com.zup.edu

import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.QueryValue
import javax.inject.Inject

@Controller
class CalculadorDeFretesController(@Inject val gRpcCliente: FretesServiceGrpc.FretesServiceBlockingStub)
 {
     @Get("/api/fretes")
     fun calcula(@QueryValue cep: String) : FreteResponse {

         val request = CalculaFreteRequest.newBuilder()
             .setCep(cep)
             .build()

         val response = gRpcCliente.calculaFrete(request)

         return FreteResponse(response.cep, response.valor)
     }

 }

data class FreteResponse(val cep: String, val valor: Double){}
