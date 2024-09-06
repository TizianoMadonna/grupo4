package utn.methodology.infrastructure.http.router

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import utn.methodology.infrastructure.persistence.connectToMongoDB
import utn.methodology.application.commands.CrearUsuarioComando
import utn.methodology.application.commandhandlers.CrearUsuarioHandler
import utn.methodology.domain.entities.Usuario
import utn.methodology.infrastructure.http.actions.ActionCrearUsuario
import utn.methodology.infrastructure.persistence.RepositorioUsuarioMongo

//falta import example.com.infrastructure.persistence.MongoUserRepository
//import example.com.infrastructure.persistence.connectToMongoDB

fun Application.UsuarioRouter(){
    val mongoDB = connectToMongoDB();
    val userMongoUserRepository = RepositorioUsuarioMongo(mongoDB)

    val ActionCrearUsuario = ActionCrearUsuario(CrearUsuarioHandler(userMongoUserRepository))

    routing {

        post("/users") {
            val body = call.receive<CrearUsuarioComando>()
            ActionCrearUsuario.ejecutar(body);

            call.respond(HttpStatusCode.Created, mapOf("mensaje" to "ok"))
        }
        get("/users") {
            val usuario = userMongoUserRepository.BuscarTodoUsuario();

            call.respond(HttpStatusCode.OK, usuario.map { it.toPrimitives() })
        }
    }
}