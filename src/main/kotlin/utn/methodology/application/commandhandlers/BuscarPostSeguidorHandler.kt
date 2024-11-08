package utn.methodology.application.commandhandlers

import io.ktor.server.plugins.*
import utn.methodology.application.commands.BuscarPostSeguidorComando
import utn.methodology.domain.entities.Post
import utn.methodology.infrastructure.persistence.RepositorioPostMongo
import utn.methodology.infrastructure.persistence.RepositorioUsuarioMongo

class BuscarPostSeguidorHandler(
    private val repositorioPost: RepositorioPostMongo,
    private val repositorioUsuario: RepositorioUsuarioMongo
) {
    fun handle(comando: BuscarPostSeguidorComando): List<Post> {
        val usuario = repositorioUsuario.BuscarUsuarioId(comando.id)
            ?: throw NotFoundException("Usuario no encontrado")

        return repositorioPost.BuscarPostsUsuariosSeguidos(usuario.getSeguidos())
    }
}