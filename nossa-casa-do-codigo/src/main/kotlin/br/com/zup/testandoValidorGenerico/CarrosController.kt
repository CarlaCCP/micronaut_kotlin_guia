package br.com.zup.testandoValidorGenerico

import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Post
import io.micronaut.validation.Validated
import javax.validation.Valid

@Validated
@Controller
class CarrosController(val carroRepository: CarroRepository) {

    @Post("/api/carros")
    fun criar(@Body @Valid carro: Carro): HttpResponse<Any> {
        carroRepository.save(carro)
        return HttpResponse.ok(carro)
    }

}