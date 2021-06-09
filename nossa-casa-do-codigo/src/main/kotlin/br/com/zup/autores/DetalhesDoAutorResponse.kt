package br.com.zup.autores

class DetalhesDoAutorResponse(autor: Autor) {

    // para ser properties precisa ser val? confirmar
    val nome = autor.nome
    val email = autor.email
    val descricao = autor.descricao
    val endereco = autor.endereco
}
