package utn.methodology

import com.mongodb.client.MongoClients
import com.mongodb.client.MongoDatabase
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertEquals
import utn.methodology.infrastructure.persistence.RepositorioUsuarioMongo
import utn.methodology.application.commandhandlers.CrearUsuarioHandler
import utn.methodology.application.commands.CrearUsuarioComando
import utn.methodology.domain.entities.Usuario

class CrearUsuarioTest {

    private lateinit var database: MongoDatabase
    private lateinit var repositorioUsuario: RepositorioUsuarioMongo
    private lateinit var crearUsuarioHandler: CrearUsuarioHandler

    @BeforeTest
    fun setUp() {
        val mongoClient = MongoClients.create("mongodb://localhost:8080")
        database = mongoClient.getDatabase("testDB")

        repositorioUsuario = RepositorioUsuarioMongo(database)
        crearUsuarioHandler = CrearUsuarioHandler(repositorioUsuario)

        database.getCollection("Usuario").drop()
    }

    @AfterTest
    fun tearDown() {
        database.getCollection("Usuario").drop()
    }

    @Test
    fun `crear usuario exitosamente`() {
        val comando = CrearUsuarioComando(
            nombre = "Juan Perez",
            usuario = "jperez",
            email = "juan.perez@example.com",
            password = "password123"
        )

        crearUsuarioHandler.handle(comando)

        val usuarioGuardado: Usuario? = repositorioUsuario.BuscarTodoUsuario().firstOrNull()

        assertNotNull(usuarioGuardado)
        assertEquals(comando.nombre, usuarioGuardado?.toPrimitives()?.get("nombre"))
        assertEquals(comando.usuario, usuarioGuardado?.toPrimitives()?.get("usuario"))
        assertEquals(comando.email, usuarioGuardado?.toPrimitives()?.get("email"))
        assertEquals(comando.password, usuarioGuardado?.toPrimitives()?.get("password"))
    }
}