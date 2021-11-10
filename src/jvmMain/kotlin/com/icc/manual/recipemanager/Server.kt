import com.icc.manual.recipemanager.model.Recipe
import com.mongodb.ConnectionString
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.http.*
import io.ktor.http.content.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.serialization.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import org.litote.kmongo.coroutine.coroutine
import org.litote.kmongo.eq
import org.litote.kmongo.reactivestreams.KMongo

val connectionString: ConnectionString? = System.getenv("MONGODB_URI")?.let {
    ConnectionString("$it?retryWrites=false")
}
val client = if (connectionString != null) KMongo.createClient(connectionString).coroutine else KMongo.createClient().coroutine
val database = client.getDatabase("recipe-manager-manual")
val collection = database.getCollection<Recipe>()

fun main() {
    val port = System.getenv("PORT")?.toInt() ?: 9090
    embeddedServer(Netty, port) {
        install(ContentNegotiation) {
            json()
        }
        install(CORS) {
            method(HttpMethod.Get)
            method(HttpMethod.Post)
            method(HttpMethod.Delete)
            anyHost()
        }
        install(Compression) {
            gzip()
        }
        routing {
            get("/") {
                call.respondText(
                    this::class.java.classLoader.getResource("index.html")!!.readText(),
                    ContentType.Text.Html
                )
            }
            static("/") {
                resources("")
            }

            route("/recipe") {
                get {
                    call.respond(collection.find().toList())
                }
                get("/{id}") {
                    val id = call.parameters["id"]!!.toInt()
                    call.respond(
                        collection.findOne(Recipe::id eq id)!!
                    )
                }
                post {
                    val recipe = call.receive<Recipe>()
                    println("Received recipe $recipe")
                    collection.insertOne(recipe)
                    call.respond(HttpStatusCode.OK)
                }
            }

        }
    }.start(wait = true)
}