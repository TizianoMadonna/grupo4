package utn.methodology.infrastructure.persistence

import com.mongodb.client.MongoDatabase
import com.mongodb.client.MongoCollection
import com.mongodb.client.model.UpdateOptions
import utn.methodology.domain.entities.Post
import org.bson.Document

class RepositorioPostMongo(private val database: MongoDatabase) {

    private var coleccion: MongoCollection<Any>;
    init{
        coleccion = database.getCollection("Post") as MongoCollection<Any>;
    }

    fun Guardar(post: Post) {
        val opciones = UpdateOptions().upsert(true);

        val filtro = Document("_id", post.getId())
        val actualizar = Document("\$set", post.toPrimitives())

        coleccion.updateOne(filtro, actualizar, opciones);
    }
    fun BuscarPostUsuario(user: String): Post?{

        val filtro = Document("_userId", user);
        val primitives = coleccion.find(filtro).firstOrNull();

        if (primitives == null){
            return null;
        }
        return Post.fromPrimitives((primitives as Map<String, String>))
    }
    fun BuscarPostContenido(contenido: String): Post?{
        val filtro = Document("_contenido", contenido);
        val primitives = coleccion.find(filtro).firstOrNull();

        if (primitives == null){
            return null;
        }
        return Post.fromPrimitives((primitives as Map<String, String>))
    }

    fun BuscarTodoPost(): List<Post> {

        val primitives = coleccion.find().map { it as Document }.toList();

        return primitives.map {
            Post.fromPrimitives(it.toMap() as Map<String, String>)
        };
    }

    fun Borrar(post: Post) {
        val filtro = Document("_id", post.getId());

        coleccion.deleteOne(filtro)
    }
    fun finOne(id: String ): Post? {
        val filtro = Document("_id",id);
        val primitives = coleccion.find(filtro).firstOrNull();
        if (primitives == null){
            return  null;
        }
        return Post.fromPrimitives(primitives as Map<String,String>)
    }
    fun findByOwnerId(ownerId: String): List<Post> {
        val filtro = Document("ownerId", ownerId);

        val primitives = coleccion.find(filtro)
            .sort(Document("createdAt", 1)).toList()

        return primitives.map { Post.fromPrimitives(it as Map<String, Any>) }
    }
    fun findByFollows(IdSeguidos: List<String>): List<Post> {
        val filtro = Document("ownerId", Document("\$in", IdSeguidos))

        val primitives = coleccion.find(filtro).sort(
            Document("fecha", 1)
        ).toList()

        return primitives.map { Post.fromPrimitives(it as Map<String, Any>) }
    }

}
