package utn.methodology.infrastructure.http.actions

import utn.methodology.application.commands.SeguirUsuarioComando
import utn.methodology.application.commandhandlers.SeguirUsuarioHandler

class ActionSeguirUsuario(private val handler: SeguirUsuarioHandler) {
    fun ejecutar(followerId: String, followingId: String) {
        val command = SeguirUsuarioComando(followerId, followingId)
        handler.handle(command)
    }
}