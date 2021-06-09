package br.com.zup.autores

import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.PathVariable
import io.micronaut.http.annotation.Put

import javax.inject.Inject
import javax.transaction.Transactional

@Controller("/autores/{id}")
class AtualizaAutoController(@Inject val autorRepository: AutorRepository) {

    @Put
    @Transactional
    fun atualiza(@PathVariable id: Long, descricao: String) : HttpResponse<Any>{

    val possivelAutor = autorRepository.findById(id)

        if(possivelAutor.isEmpty){
            return HttpResponse.notFound()
        }

        val autor = possivelAutor.get()
        autor.descricao = descricao
        //autorRepository.update(autor) - não precisa, por causa do transactional
        //assim que terminar  a transação, o próprio jpa irá fazer o update dos dados novos
        return HttpResponse.ok(DetalhesDoAutorResponse(autor))
    }
}