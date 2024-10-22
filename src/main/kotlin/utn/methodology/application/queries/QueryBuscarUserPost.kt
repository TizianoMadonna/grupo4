package utn.methodology.application.queries

class QueryBuscarUserPost (val userId: String){
    fun validar(): QueryBuscarUserPost{
        checkNotNull(userId){
            throw IllegalArgumentException("Usuario no encontrado");
        }
        return this;

    }
}