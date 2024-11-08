package utn.methodology.application.commandhandlers

import io.ktor.server.plugins.*
import utn.methodology.application.commands.CrearPostComand;
import java.util.*
import utn.methodology.domain.entities.Post
import utn.methodology.infrastructure.persistence.RepositorioPostMongo


class CrearPostHandler(private val RepositorioPost: RepositorioPostMongo) {
    fun handle (command: CrearPostComand){
        val UsuarioBuscado = this.RepositorioUsuario.BuscarUsuarioId(command.userId);
        if(UsuarioBuscado == null){
            throw NotFoundException("Usuario no encontrado");
        }
        val post = Post(
            UUID.randomUUID().toString(),
            command.userId,
            command.contenido,
            command.fecha
        )
        this.RepositorioPost.Guardar(post);
    }
}