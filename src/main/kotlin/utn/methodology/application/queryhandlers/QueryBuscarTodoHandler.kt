package utn.methodology.application.queryhandlers

import utn.methodology.application.queries.QueryBuscarTodo
import io.ktor.server.plugins.*
import utn.methodology.infrastructure.persistence.RepositorioUsuarioMongo

class QueryBuscarTodoHandler(private val UsuarioRepositorio: RepositorioUsuarioMongo) {
    fun handle(query: QueryBuscarTodo): Map<String, String>{
    val usuario = UsuarioRepositorio.BuscarUsuarioId(query.id);
        if (usuario == null)
        {
            throw NotFoundException("Usuario con ID: ${query.id} no encontrado");
        }
        return usuario.toPrimitives();
    }
}