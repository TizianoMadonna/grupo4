package utn.methodology.infrastructure.persistence

import com.mongodb.client.MongoDatabase
import com.mongodb.client.MongoCollection
import com.mongodb.client.model.Filters
import com.mongodb.client.model.UpdateOptions
import com.mongodb.client.model.Updates
import utn.methodology.domain.entities.Usuario
import org.bson.Document


class RepositorioUsuarioMongo(private val database: MongoDatabase) {

    private var coleccion: MongoCollection<Any>;
    private val usuarios: MutableMap<Long, Usuario> = mutableMapOf()
    init{
        coleccion = database.getCollection("Usuario") as MongoCollection<Any>;
    }

    fun Guardar(usuario: Usuario) {
        val opciones = UpdateOptions().upsert(true);

        val filtro = Document("_id", usuario.getId())
        val actualizar = Document("\$set", usuario.toPrimitives())

        coleccion.updateOne(filtro, actualizar, opciones)
    }

    fun BuscarUsuarioId(id: String): Usuario? {
        val filtro = Document("_id", id);

        val primitives = coleccion.find(filtro).firstOrNull();

        if (primitives == null) {
            return null;
        }

        return Usuario.fromPrimitives(primitives as Map<String, String>)
    }

    fun BuscarTodoUsuario(): List<Usuario> {

        val primitives = coleccion.find().map { it as Document }.toList();

        return primitives.map {
            Usuario.fromPrimitives(it.toMap() as Map<String, String>)
        };
    }

    fun Borrar(usuario: Usuario) {
        val filtro = Document("_id", usuario.getId());

        coleccion.deleteOne(filtro)
    }
    fun AgregarSeguidor(followerId: String, followingId: String){
        val filtroSeguidor = Filters.eq("_id", followerId)
        val filtroSeguido = Filters.eq("_id", followingId)

        coleccion.updateOne(filtroSeguidor, Updates.addToSet("seguidos", followingId))

        coleccion.updateOne(filtroSeguido, Updates.addToSet("seguidores", followerId))
    }
    fun EliminarSeguidor(followerId: String, followingId: String) {
        val filtroSeguidor = Filters.eq("_id", followerId)
        val filtroSeguido = Filters.eq("_id", followingId)

        coleccion.updateOne(filtroSeguidor, Updates.pull("seguidos", followingId))

        coleccion.updateOne(filtroSeguido, Updates.pull("seguidores", followerId))
    }
    fun existeUsuarioConId(id: String): Boolean {
        val filtro = Filters.eq("_id", id)
        return coleccion.find(filtro).firstOrNull() != null
    }
}