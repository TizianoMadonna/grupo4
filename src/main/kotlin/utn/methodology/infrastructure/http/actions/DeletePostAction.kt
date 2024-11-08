package utn.methodology.infrastructure.http.actions

import utn.methodology.application.commands.DeletePostComando
import  utn.methodology.application.commandhandlers.DeletePostHandler

class DeletePostAction(private val handler: DeletePostHandler) {

    fun ejecutar(comando: DeletePostComando){
        comando.validar().let {
            handler.handle(it)
        }
    }
}