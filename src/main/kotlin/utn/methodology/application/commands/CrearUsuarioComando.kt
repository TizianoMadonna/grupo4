package utn.methodology.application.commands;

import kotlinx.serialization.Serializable;

@Serializable()
class CrearUsuarioComando(
    val nombre: String,
    val usuario: String,
    val email: String,
    val password: String
)
{
    fun validarCampos(): CrearUsuarioComando
    {
        checkNotNull(nombre){throw IllegalArgumentException("Campo vacío")}
        checkNotNull(usuario){throw IllegalArgumentException("Campo vacío")}
        checkNotNull(email){throw IllegalArgumentException("Campo vacío")}
        checkNotNull(password){throw IllegalArgumentException("Campo vacío")}
        return this;
    }
}