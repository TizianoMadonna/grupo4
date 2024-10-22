package utn.methodology.domain.entities

import java.util.*

class Usuario(
    val id: UUID? = null,
    val nombre: String,
    val usuario: String,
    val email: String,
    val password: String
)
