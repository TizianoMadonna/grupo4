package utn.methodology.application.queryhandlers

import utn.methodology.application.queries.QueryBuscarContenidoPost
import utn.methodology.infrastructure.persistence.RepositorioPostMongo
import io.ktor.server.plugins.*

class QueryBuscarContenidoPostHandler( private val repositorio: RepositorioPostMongo){
    fun handle(query: QueryBuscarContenidoPost): Map<String, String>{
        val post = repositorio.BuscarPostContenido(query.contenido)

        if (post == null){
            throw NotFoundException("Contenido no encontrado")
        }
        return post.toPrimitives()
    }
}