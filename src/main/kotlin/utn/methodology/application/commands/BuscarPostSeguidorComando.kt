package utn.methodology.application.commands

class BuscarPostSeguidorComando (val id: String) {
    fun validar(): BuscarPostSeguidorComando {
        checkNotNull(id) { throw IllegalArgumentException("Usuario no definido") }
        return this
    }
}