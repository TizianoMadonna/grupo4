package utn.methodology.application.queries

class QueryBuscarContenidoPost (val contenido: String){
    fun validar(): QueryBuscarContenidoPost{
        checkNotNull(contenido){
            throw IllegalArgumentException("Contenido no encontrado");
        }
        return this;

    }
}