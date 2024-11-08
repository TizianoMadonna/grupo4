package utn.methodology.infrastructure.http.router

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import utn.methodology.infrastructure.persistence.connectToMongoDB
import utn.methodology.application.commands.CrearUsuarioComando
import utn.methodology.application.commandhandlers.CrearUsuarioHandler
import utn.methodology.infrastructure.http.actions.ActionCrearUsuario
import utn.methodology.infrastructure.persistence.RepositorioUsuarioMongo
import utn.methodology.application.commandhandlers.SeguirUsuarioHandler
import utn.methodology.application.commandhandlers.DejarSeguirUsuarioHandler
import utn.methodology.infrastructure.http.actions.ActionSeguirUsuario
import utn.methodology.infrastructure.http.actions.ActionDejarSeguirUsuario

fun Application.UsuarioRouter(){
    val mongoDB = connectToMongoDB();
    val userMongoUserRepository = RepositorioUsuarioMongo(mongoDB)

    val ActionCrearUsuario = ActionCrearUsuario(CrearUsuarioHandler(userMongoUserRepository))

    val seguirUsuarioHandler = SeguirUsuarioHandler(userMongoUserRepository)
    val dejarSeguirUsuarioHandler = DejarSeguirUsuarioHandler(userMongoUserRepository)
    val actionSeguirUsuario = ActionSeguirUsuario(seguirUsuarioHandler)
    val actionDejarSeguirUsuario = ActionDejarSeguirUsuario(dejarSeguirUsuarioHandler)

    routing {

        post("/users") {
            val body = call.receive<CrearUsuarioComando>()
            ActionCrearUsuario.ejecutar(body);

            call.respond(HttpStatusCode.Created, mapOf("mensaje" to "ok"))
        }
        get("/users") {
            val usuario = userMongoUserRepository.BuscarTodoUsuario();

            call.respond(HttpStatusCode.OK, usuario.map {
                mapOf("id" to it.getId(),
                "nombre" to it.getNombre(),
                "usuario" to it.getUsuario(),
                "password" to it.getPassword(),
                "email" to it.getEmail()) })
        }
        post("/users/{followerId}/follow/{followingId}") {
            val followerId = call.parameters["followerId"]!!
            val followingId = call.parameters["followingId"]!!

            if(followerId == null){
                call.respond(HttpStatusCode.BadRequest, mapOf("mensaje" to "Usuario que sigue no encontrado"))
                return@post
            }
            if(followingId == null){
                call.respond(HttpStatusCode.BadRequest, mapOf("mensaje" to "Usuario a seguir no encontrado"))
                return@post
            }
            val usuarioSeguidor = userMongoUserRepository.existeUsuarioConId(followerId)
            val usuarioSeguido = userMongoUserRepository.existeUsuarioConId(followingId)

            if(!usuarioSeguidor){
                call.respond(HttpStatusCode.NotFound, mapOf("mensaje" to "Usuario seguidor no existe"))
                return@post
            }
            if(!usuarioSeguido){
                call.respond(HttpStatusCode.NotFound, mapOf("mensaje" to "Usuario seguido no existe"))
                return@post
            }

            actionSeguirUsuario.ejecutar(followerId, followingId)

            call.respond(HttpStatusCode.OK, mapOf("mensaje" to "Usuario seguido con éxito"))
        }
        post("/users/{followerId}/unfollow/{followingId}") {
            val followerId = call.parameters["followerId"]!!
            val followingId = call.parameters["followingId"]!!
            if(followerId == null){
                call.respond(HttpStatusCode.BadRequest, mapOf("mensaje" to "Usuario que sigue no encontrado"))
                return@post
            }
            if(followingId == null){
                call.respond(HttpStatusCode.BadRequest, mapOf("mensaje" to "Usuario a seguir no encontrado"))
                return@post
            }
            val usuarioSeguidor = userMongoUserRepository.existeUsuarioConId(followerId)
            val usuarioSeguido = userMongoUserRepository.existeUsuarioConId(followingId)

            if(!usuarioSeguidor){
                call.respond(HttpStatusCode.NotFound, mapOf("mensaje" to "Usuario seguidor no existe"))
                return@post
            }
            if(!usuarioSeguido){
                call.respond(HttpStatusCode.NotFound, mapOf("mensaje" to "Usuario seguido no existe"))
                return@post
            }
            actionDejarSeguirUsuario.ejecutar(followerId, followingId)

            call.respond(HttpStatusCode.OK, mapOf("mensaje" to "Usuario dejado de seguir con éxito"))
        }
    }
}
