package utn.methodology.infrastructure.http.router

import io.ktor.server.application.*
import utn.methodology.infrastructure.persistence.connectToMongoDB

fun Application.UsuarioRouter(){
    val mongoDB = connectToMongoDB();
}