package bitco.ua

import bitco.ua.plugins.configureRouting
import bitco.ua.plugins.configureSerialization
import io.ktor.server.engine.*
import io.ktor.server.cio.*

fun main() {
    embeddedServer(CIO, port = 8080, host = "0.0.0.0") {
        configureRouting()
        configureSerialization()
    }.start(wait = true)
}
