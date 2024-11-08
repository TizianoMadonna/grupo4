package utn.methodology.application.commandhandlers

import io.ktor.server.plugins.*
import utn.methodology.application.commands.DeletePostComando
import utn.methodology.infrastructure.persistence.RepositorioPostMongo


class DeletePostHandler(private val repositorio : RepositorioPostMongo) {
    fun handle(comando : DeletePostComando){
        val post = repositorio.findOne(comando.id)
        if(post == null){
            throw NotFoundException("No se encotro el post como id: ${comando.id}")
        }
        repositorio.Borrar(post);

    }
}