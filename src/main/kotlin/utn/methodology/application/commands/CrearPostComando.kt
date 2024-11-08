package utn.methodology.application.commands;

import kotlinx.serialization.Serializable;
import java.time.LocalDateTime

@Serializable()
class CrearPostComand(
    var userId: String,
    var contenido: String,
    val fecha: String = LocalDateTime.now().toString()
) {

    fun validarCampos(): CrearPostComand {
        checkNotNull(userId) { throw IllegalArgumentException("Campo vacío") }
        checkNotNull(contenido) { throw IllegalArgumentException("Campo vacío") }
        checkNotNull(fecha) { throw IllegalArgumentException("Campo vacío") }
        return this;
    }
}