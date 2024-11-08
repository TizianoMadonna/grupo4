package utn.methodology.infrastructure.http.actions

import utn.methodology.application.commands.SeguirUsuarioComando
import utn.methodology.application.commandhandlers.SeguirUsuarioHandler
import utn.methodology.application.commands.DejarSeguirUsuarioComando
import utn.methodology.application.commandhandlers.DejarSeguirUsuarioHandler

class ActionDejarSeguirUsuario(private val handler: DejarSeguirUsuarioHandler) {
    fun ejecutar(followerId: String, followingId: String) {
        val command = DejarSeguirUsuarioComando(followerId, followingId)
        handler.handle(command)
    }
}