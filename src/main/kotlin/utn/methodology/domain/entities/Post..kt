package utn.methodology.domain.entities

import java.time.LocalDateTime

class Post (
    private val uuid: String,
    private var userId: String,
    private var contenido: String,
    private val fecha: String = LocalDateTime.now().toString()
) {
    companion object {
        fun fromPrimitives(primitives: Map<String, Any>): Post {
            val post = Post(
                primitives["id"] as String,
                primitives["userId"] as String,
                primitives["contenido"] as String,
                primitives["fecha"] as String
            );
            return post;
        }
    }
    fun getContenido(): String{
        return this.contenido;
    }
    fun getId(): String {
        return this.uuid;
    }

    fun update(contenido: String) {
        this.contenido = contenido;
    }

    fun toPrimitives(): Map<String, String> {
        return mapOf(
            "id" to this.uuid,
            "userId" to this.userId,
            "contenido" to this.contenido,
            "fecha" to this.fecha
        )
    }
}