package utn.methodology.application.queryhandlers

import io.ktor.server.plugins.*
import utn.methodology.application.queries.QueryBuscarUserPost
import utn.methodology.infrastructure.persistence.RepositorioPostMongo

class QueryBuscarUsuarioPostHandler(private val repositorio: RepositorioPostMongo) {
    fun handle(query: QueryBuscarUserPost): Map<String, String>{
        val post = repositorio.BuscarPostUsuario(query.userId)

        if (post == null){
            throw NotFoundException("Contenido no encontrado")
        }
        return post.toPrimitives()
    }
}