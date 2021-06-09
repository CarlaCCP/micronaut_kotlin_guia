package br.com.zup.testandoValidorGenerico

import io.micronaut.test.annotation.TransactionMode
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import javax.inject.Inject

// Para desligar o rollback- para ter mais controle
// transactionmode - faz com que a transacao de beforeEach ocorra junto com os testes
// transactionl = todos os testes serão autocommit
@MicronautTest(
    rollback = false,
    transactionMode = TransactionMode.SINGLE_TRANSACTION,
    transactional = false
)
//Minuto 19:55
internal class CarrosControllerTest{

    @field:Inject
    lateinit var repository: CarroRepository

    // por se repitir, melhor colocar em um método
    //será chamado antes de todos os testes
    @BeforeEach
    fun setup(){
        repository.deleteAll()
    }

    @Test
    fun `deve inserir um novo carro`(){

        repository.save(Carro(modelo = "Gollllll", placa = "AAA1234"))

        assertEquals(1, repository.count())
    }

    @Test
    fun `deve encontrar carro pro placa`(){

        //cenario
        repository.save(Carro(modelo = "Gollllll", placa = "AAA1234"))

        // acao
        val encontrado = repository.existsByPlaca("AAA1234")

        // validacao
        assertTrue(encontrado)
    }

}
