package utn.methodology.infrastructure.persistence

import com.mongodb.client.MongoDatabase
import com.mongodb.client.MongoCollection
import com.mongodb.client.model.UpdateOptions
import utn.methodology.domain.entities.Post
import org.bson.Document

@Suppress("UNCHECKED_CAST")
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
    fun findOne(id: String): Post? {
        val filtro = Document("_id", id)  // Ensure `_id` is correct
        val primitives = coleccion.find(filtro).firstOrNull()

        return primitives?.let { Post.fromPrimitives(it.toString() as Map<String, String>) }
    }
    fun BuscarPostsUsuariosSeguidos(id: List<String>): List<Post> {
        val filtro = Document("_userId", Document("\$in", id))
        val posts = coleccion.find(filtro).sort(Document("fecha", -1)).map { it as Document }.toList()

        return posts.map {
            Post.fromPrimitives(it.toMap().mapValues { entry -> entry.value.toString() } as Map<String, String>)
        }
    }
}
