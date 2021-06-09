package br.com.zup.autores

import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.QueryValue
import java.net.http.HttpRequest
import javax.inject.Inject
import javax.transaction.Transactional
import javax.validation.constraints.Email

@Controller("/autores")
class BuscaAutoresController(@Inject val autorRepository: AutorRepository) {

    @Get
    @Transactional
    fun lista(@QueryValue(defaultValue = "") email: String) : HttpResponse<Any> {

        if(email.isBlank()){
            val autores = autorRepository.findAll()

            val response = autores.map { autor -> DetalhesDoAutorResponse(autor) }
            return HttpResponse.ok(response)
        }
        //val possivelAutor = autorRepository.findByEmail(email)
        // ou
         val possivelAutor = autorRepository.buscaPorEmail(email)
        if(possivelAutor.isEmpty){
            return HttpResponse.notFound()
        }

        val autor = possivelAutor.get()
        return HttpResponse.ok(DetalhesDoAutorResponse(autor))

    }



}