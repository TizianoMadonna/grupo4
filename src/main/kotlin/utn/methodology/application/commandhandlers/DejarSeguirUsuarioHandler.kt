    package utn.methodology.application.commandhandlers

    import utn.methodology.infrastructure.persistence.RepositorioUsuarioMongo
    import utn.methodology.application.commands.DejarSeguirUsuarioComando

    class DejarSeguirUsuarioHandler (private val UsuarioRepositorio: RepositorioUsuarioMongo){
        fun handle(comando: DejarSeguirUsuarioComando){
            UsuarioRepositorio.EliminarSeguidor(comando.followerId, comando.followingId)
        }
    }