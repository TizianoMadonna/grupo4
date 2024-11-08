package utn.methodology

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import utn.methodology.application.commandhandlers.CrearPostHandler
import utn.methodology.application.commands.CrearPostComand
import utn.methodology.infrastructure.persistence.RepositorioPostMongo
import com.mongodb.client.MongoClients
import com.mongodb.client.MongoDatabase
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import utn.methodology.domain.entities.Post

class CrearPostTest {
    private lateinit var database: MongoDatabase
    private lateinit var reporitorioPost: RepositorioPostMongo
    private lateinit var crearPostHandler: CrearPostHandler

    @BeforeTest
    fun setUp() {
        val mongoClient = MongoClients.create("mongodb://localhost:8080")
        database = mongoClient.getDatabase("testDB")

        reporitorioPost = RepositorioPostMongo(database)
        crearPostHandler = CrearPostHandler(reporitorioPost)

        database.getCollection("Post").drop()
    }

    @AfterTest
    fun tearDown() {
        database.getCollection("Post").drop()
    }

    @Test
    fun `crear post exitosamente`() {
        val comando = CrearPostComand(
            userId = "Tiziano Madonna",
            contenido = "¿Como anda la banda?",
            fecha = "2024-02-02"
        )

        crearPostHandler.handle(comando)

        val usuarioGuardado: Post? = reporitorioPost.BuscarTodoPost().firstOrNull()

        assertNotNull(usuarioGuardado)
        assertEquals(comando.userId, usuarioGuardado?.toPrimitives()?.get("userId"))
        assertEquals(comando.contenido, usuarioGuardado?.toPrimitives()?.get("contenido"))
        assertEquals(comando.fecha, usuarioGuardado?.toPrimitives()?.get("fecha"))
    }
}