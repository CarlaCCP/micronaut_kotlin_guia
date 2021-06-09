package br.com.zup.autores

import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Delete
import io.micronaut.http.annotation.PathVariable
import javax.transaction.Transactional


@Controller("/autores/{id}")
class DeletarAutorController(val autorRepository: AutorRepository) {


    @Delete
    @Transactional
    fun deleta(@PathVariable id: Long) : HttpResponse<Any>{
        val possivelAutor = autorRepository.findById(id)

        if(possivelAutor.isEmpty){
            return HttpResponse.notFound()
        }
        // ou
        // val autor = possivelAutor.get()
        // autorRepository.delete(autor)
        autorRepository.deleteById(id)
        return HttpResponse.ok()
    }
}