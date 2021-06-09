package br.com.zup.autores

import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.HttpStatus
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.test.annotation.MockBean
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import javax.inject.Inject


@MicronautTest
internal class CadastraAutorControllerTest{

    @field:Inject
    lateinit var enderecoClient: EnderecoClient

    // lateinit var serve para inicializar depois a variavel
    @field:Inject
    @field:Client("/")
    lateinit var client: HttpClient


    @Test
    fun `deve cadastrar um novo autor`(){
        // cenario
        val novoAutorRequest = NovoAutoRequest(
            "Carla Cristina",
            "carla@carla.com",
            "Teste",
            "07130230",
            "124"
        )
        val request = HttpRequest.POST("/autores", novoAutorRequest)

        val enderecoResponse = EnderecoResponse("R. Caracas", "Guarulhos", "SP")
        Mockito.`when`(enderecoClient.consulta(novoAutorRequest.cep)).thenReturn(HttpResponse.ok(enderecoResponse))


        // acao

        val response = client.toBlocking().exchange(request, Any::class.java)
        // corretude
        assertEquals(HttpStatus.CREATED, response.status)
        assertTrue(response.headers.contains("Location"))
        assertTrue(response.header("Location").matches("/autores/\\d".toRegex()))

    }

    //o que é mockar - imita uma outra classe
    @MockBean(EnderecoClient::class)
    fun enderecoMock() : EnderecoClient{
        return Mockito.mock(EnderecoClient::class.java)
    }

}