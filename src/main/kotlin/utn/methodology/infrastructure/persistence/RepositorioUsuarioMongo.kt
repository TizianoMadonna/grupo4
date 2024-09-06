package utn.methodology.infrastructure.persistence

import com.mongodb.client.MongoDatabase
import com.mongodb.client.MongoCollection
import com.mongodb.client.model.UpdateOptions
import utn.methodology.domain.entities.Usuario
import org.bson.Document

class RepositorioUsuarioMongo(private val database: MongoDatabase) {

    private var coleccion: MongoCollection<Any>;
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
        val filtro = Document("_id", Usuario.getId());

        coleccion.deleteOne(filtro)
    }

}