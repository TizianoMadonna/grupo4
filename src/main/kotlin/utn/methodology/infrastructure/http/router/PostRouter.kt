package utn.methodology.infrastructure.http.router

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import utn.methodology.application.commandhandlers.BuscarPostSeguidorHandler
import utn.methodology.infrastructure.persistence.connectToMongoDB
import utn.methodology.infrastructure.persistence.RepositorioUsuarioMongo
import utn.methodology.application.commandhandlers.CrearPostHandler
import utn.methodology.application.commandhandlers.DeletePostHandler
import utn.methodology.application.commands.BuscarPostSeguidorComando
import utn.methodology.infrastructure.http.actions.ActionCrearPost
import utn.methodology.infrastructure.persistence.RepositorioPostMongo
import utn.methodology.application.commands.CrearPostComand
import utn.methodology.application.commands.DeletePostComando
import utn.methodology.infrastructure.http.actions.DeletePostAction

fun Application.PostRouter(){
    val mongoDB = connectToMongoDB();
    val postMongoRepository = RepositorioPostMongo(mongoDB)
    val usuarioMongoRepository = RepositorioUsuarioMongo(mongoDB)

    val buscarPostSeguidorHandler = BuscarPostSeguidorHandler(postMongoRepository, usuarioMongoRepository)


    val actionCrearPost = ActionCrearPost(CrearPostHandler(postMongoRepository))

    routing {

        post("/posts") {
            val body = call.receive<CrearPostComand>()
            actionCrearPost.ejecutar(body);

            call.respond(HttpStatusCode.Created, mapOf("mensaje" to "Post creado"))
        }
        get("/posts") {
            val post = postMongoRepository.BuscarTodoPost();

            call.respond(HttpStatusCode.OK, post.map { it.toPrimitives() })
        }

        delete("/posts/{id}"){

            val deletePostAction = DeletePostAction(DeletePostHandler(RepositorioPostMongo(mongoDB)))
            val body = call.receive<DeletePostComando>()

            deletePostAction.ejecutar(body)
            call.respond(HttpStatusCode.OK, mapOf("mensaje" to "Post eliminado"))
        }

        get("/posts/user/{userId}") {
            val userId = call.parameters["userId"] ?: throw BadRequestException("Usuario no encontrado")

            val comando = BuscarPostSeguidorComando(userId).validar()
            val posts = buscarPostSeguidorHandler.handle(comando)

            call.respond(HttpStatusCode.OK, posts.map { it.toPrimitives() })
        }
    }
}