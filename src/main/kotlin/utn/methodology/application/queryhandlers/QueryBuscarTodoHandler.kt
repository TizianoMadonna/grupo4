package utn.methodology.application.queryhandlers

import utn.methodology.application.queries.QueryBuscarTodo
import io.ktor.server.plugins.*
import utn.methodology.infrastructure.persistence.RepositorioUsuarioMongo

class QueryBuscarTodoHandler(private val UsuarioRepositorio: RepositorioUsuarioMongo) {
    fun handle(query: QueryBuscarTodo): Map<String, Any>{
    val usuario = UsuarioRepositorio.BuscarUsuarioId(query.id);
        if (usuario == null)
        {
            throw NotFoundException("Usuario con ID: ${query.id} no encontrado");
        }
        return mapOf("id" to usuario.getId(),
            "nombre" to usuario.getId(),
            "usuario" to usuario.getId(),
            "password" to usuario.getId(),
            "email" to usuario.getId());
    }
}