package utn.methodology.application.commandhandlers;

import utn.methodology.application.commands.CrearUsuarioComando
import utn.methodology.infrastructure.persistence.RepositorioUsuarioMongo;
import java.util.*
import utn.methodology.domain.entities.Usuario

class CrearUsuarioHandler (private val RepositorioUsuario: RepositorioUsuarioMongo)
{
    fun handle (command: CrearUsuarioComando){
        val usuario = Usuario(
        UUID.randomUUID(),
        command.usuario,
        command.nombre,
        command.email,
        command.password
        )
        RepositorioUsuario.Guardar(usuario);
    }
}