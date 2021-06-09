package br.com.zup.autores

import io.micronaut.http.HttpStatus
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import javax.inject.Inject

@MicronautTest // para o micronaut subir um contexto (?), pra fazer injenções de dependência
internal class BuscaAutoresControllerTest{

    @field: Inject
    lateinit var autorRepository: AutorRepository

    lateinit var autor: Autor

    //injeta um cliente
    @field:Inject
    @field:Client("/")
    lateinit var client: HttpClient

    @BeforeEach
    internal fun setup(){
        val enderecoResponse = EnderecoResponse("R Tal", "Tal", "SP")
        val endereco = Endereco(enderecoResponse,"28", "5468645")
        autor = Autor("Carla Cristina", "carla@carla.com", "Testando teste", endereco)
        autorRepository.save(autor)
    }

    @AfterEach
    internal fun tearDown(){
        autorRepository.deleteAll()
    }

    // minuto 10:04
    @Test
    internal fun `deve retornar os detalhes de um autor`(){
        val response = client.toBlocking() // quer dizer que vai ser executado na mesma thred do programa
            .exchange("/autores?email=${autor.email}",
                DetalhesDoAutorResponse::class.java)

        assertEquals(HttpStatus.OK, response.status)
        assertNotNull(response.body())
        assertEquals(autor.nome, response.body()!!.nome)
        assertEquals(autor.email, response.body()!!.email)
        assertEquals(autor.descricao, response.body()!!.descricao)
    }


}