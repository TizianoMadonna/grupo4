package utn.methodology.domain.entities

class Usuario(
    private val uuid: String,
    private var nombre: String,
    private var usuario: String,
    private val email: String,
    private var password: String,
    private var seguidores: MutableList<String> = mutableListOf(),
    private var seguidos: MutableList<String> = mutableListOf()
) {
    companion object {
        fun fromPrimitives(primitives: Map<String, String>): Usuario {
            val usuario = Usuario(
                primitives["id"] as String,
                primitives["nombre"] as String,
                primitives["usuario"] as String,
                primitives["email"] as String,
                primitives["password"] as String,
                if(primitives["seguidores"] == null) mutableListOf() else primitives["seguidores"] as MutableList<String>,
                if(primitives["seguidos"] == null) mutableListOf() else primitives["seguidos"] as MutableList<String>
            );
            return usuario;
        }
    }

    fun getSeguidores(): List<String>{
        return seguidores
    }
    fun getSeguidos() : List<String>{
        return seguidos
    }
    fun getId(): String {
        return this.uuid;
    }
    fun getNombre(): String {
        return this.nombre;
    }
    fun getUsuario(): String {
        return this.usuario;
    }
    fun getPassword(): String {
        return this.password;
    }
    fun getEmail(): String {
        return this.email;
    }
    fun update(nombre: String, usuario: String) {
        this.nombre = nombre;
        this.usuario = usuario;
    }

    fun toPrimitives(): Map<String, Any> {
        return mapOf(
            "id" to this.uuid,
            "nombre" to this.nombre,
            "usuario" to this.usuario,
            "email" to this.email,
            "password" to this.password,
            "seguidores" to this.seguidores,
            "seguidos" to this.seguidos
        )
    }
}