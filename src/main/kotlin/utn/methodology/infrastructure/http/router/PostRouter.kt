package utn.methodology.infrastructure.http.router

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import utn.methodology.infrastructure.persistence.connectToMongoDB
import utn.methodology.infrastructure.persistence.RepositorioUsuarioMongo
import utn.methodology.application.commandhandlers.CrearPostHandler
import utn.methodology.infrastructure.http.actions.ActionCrearPost
import utn.methodology.infrastructure.persistence.RepositorioPostMongo
import utn.methodology.application.commands.CrearPostComand

fun Application.PostRouter(){
    val mongoDB = connectToMongoDB();
    val postMongoRepository = RepositorioPostMongo(mongoDB)
    val usuarioMongoRepository = RepositorioUsuarioMongo(mongoDB)

    val actionCrearPost = ActionCrearPost(CrearPostHandler(postMongoRepository, usuarioMongoRepository))

    routing {

        post("/posts") {
            val body = call.receive<CrearPostComand>()
            actionCrearPost.ejecutar(body);

            call.respond(HttpStatusCode.Created, mapOf("mensaje" to "ok"))
        }
        get("/posts") {
            val post = postMongoRepository.BuscarTodoPost();

            call.respond(HttpStatusCode.OK, post.map { it.toPrimitives() })
        }
    }
}