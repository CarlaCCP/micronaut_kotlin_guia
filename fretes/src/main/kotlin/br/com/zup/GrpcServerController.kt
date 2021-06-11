package br.com.zup

import io.micronaut.grpc.server.GrpcEmbeddedServer
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import javax.inject.Inject

@Controller
class GrpcServerController(@Inject val grpcServer: GrpcEmbeddedServer) {

    //Só pra testar a mensagem do health, quando o servidor caiu
    @Get("/grpc-server/stop")
    fun stop() : HttpResponse<String>{
         grpcServer.stop()
        return HttpResponse.ok("Está rodando? ${grpcServer.isRunning}")
    }
}