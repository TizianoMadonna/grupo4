package utn.methodology.application.commandhandlers

import utn.methodology.infrastructure.persistence.RepositorioUsuarioMongo
import utn.methodology.application.commands.SeguirUsuarioComando

class SeguirUsuarioHandler (private val usuarioRepositorio : RepositorioUsuarioMongo){
    fun handle(comando: SeguirUsuarioComando){
        usuarioRepositorio.AgregarSeguidor(comando.followerId, comando.followingId)
    }
}