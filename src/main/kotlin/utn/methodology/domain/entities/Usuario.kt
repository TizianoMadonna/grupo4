package utn.methodology.domain.entities

import java.util.*

class Usuario(
    private var uuid: UUID?,
    private val nombre: String,
    private val usuario: String,
    private var email: String,
    private val password: String
)
{
    companion object{
        fun fromPrimitives(primitives: Map<String, String>): Usuario {
            val usuario = Usuario(
                primitives["id"] as UUID,
                primitives["nombre"] as String,
                primitives["usuario"] as String,
                primitives["email"] as String,
                primitives["password"] as String
            );
            return usuario;
        }
    }
}
fun getId(): String{
    return this.id;
}
fun update(nombre: String, usuario: String){
    this.nombre = nombre;
    this.usuario = usuario;
}
fun toPrimitives(): Map<String, String>{
return mapOf(
    "id" to this.id,
    "nombre" to this.nombre,
    "usuario" to this.usuario,
    "email" to this.email,
    "password" to this.password
    )
}