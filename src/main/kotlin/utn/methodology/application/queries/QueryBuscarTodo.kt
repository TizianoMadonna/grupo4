package utn.methodology.application.queries


class QueryBuscarTodo(val id: String) {
    fun validar(): QueryBuscarTodo {
        checkNotNull(id) {
            throw IllegalArgumentException("Id no encontrado")}
        return this;
    }
}
