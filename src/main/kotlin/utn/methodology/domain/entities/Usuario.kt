package utn.methodology.domain.entities


class Usuario(
    private val uuid: String,
    private var nombre: String,
    private var usuario: String,
    private val email: String,
    private var password: String
) {
    companion object {
        fun fromPrimitives(primitives: Map<String, String>): Usuario {
            val usuario = Usuario(
                primitives["id"] as String,
                primitives["nombre"] as String,
                primitives["usuario"] as String,
                primitives["email"] as String,
                primitives["password"] as String
            );
            return usuario;
        }
    }

    fun getId(): String {
        return this.uuid;
    }

    fun update(nombre: String, usuario: String) {
        this.nombre = nombre;
        this.usuario = usuario;
    }

    fun toPrimitives(): Map<String, String> {
        return mapOf(
            "id" to this.uuid,
            "nombre" to this.nombre,
            "usuario" to this.usuario,
            "email" to this.email,
            "password" to this.password
        )
    }
}