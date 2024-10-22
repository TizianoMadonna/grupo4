package utn.methodology.infrastructure.http.actions

import utn.methodology.application.commands.CrearUsuarioComando
import utn.methodology.application.commandhandlers.CrearUsuarioHandler

class ActionCrearUsuario(private val handler: CrearUsuarioHandler){
    fun Ejecutar(body: CrearUsuarioComando){
        body.validarCampos().let{
            handler.handle(it)
        }
    }
}