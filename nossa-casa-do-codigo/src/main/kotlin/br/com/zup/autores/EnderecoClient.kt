package br.com.zup.autores

import io.micronaut.http.HttpResponse
import io.micronaut.http.MediaType
import io.micronaut.http.annotation.Consumes
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.PathVariable
import io.micronaut.http.client.annotation.Client
import javax.print.attribute.standard.Media


@Client("https://viacep.com.br/ws")
interface EnderecoClient {

    @Get("/{cep}/json/")
    fun consulta(@PathVariable cep: String) : HttpResponse<EnderecoResponse>

    // no caso de xml
    @Get(consumes =[MediaType.APPLICATION_XML])
    // ou
    //@Consumes(MediaType.APPLICATION_XML)
    fun consultaXML (cep:String) : HttpResponse<EnderecoResponse>
}