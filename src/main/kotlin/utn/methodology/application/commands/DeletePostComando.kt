package utn.methodology.application.commands

class DeletePostComando(val id: String) {
    fun validar(): DeletePostComando{
        checkNotNull(id){throw IllegalArgumentException("Id no definido")}
        return this
    }
}