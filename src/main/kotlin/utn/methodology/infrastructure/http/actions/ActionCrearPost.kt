package utn.methodology.infrastructure.http.actions

import utn.methodology.application.commandhandlers.CrearPostHandler
import utn.methodology.application.commands.CrearPostComand

class ActionCrearPost(private val handler: CrearPostHandler) {
    fun ejecutar(body: CrearPostComand){
        body.validarCampos().let{
            handler.handle(it);
        }
    }
}