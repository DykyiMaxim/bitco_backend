package bitco.ua

import authentication.JwtService
import authentication.hash
import bitco.ua.reposotory.DataBaseFactory
import bitco.ua.reposotory.repo
import io.ktor.auth.*
import io.ktor.auth.jwt.*
import io.ktor.gson.*
import io.ktor.http.*
import io.ktor.locations.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.locations.*
import io.ktor.server.locations.KtorExperimentalLocationsAPI
import io.ktor.server.locations.Locations
import io.ktor.server.netty.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import routes.UserRoutes


fun main() {
    embeddedServer(Netty, port = System.getenv("PORT").toInt()) {

        @OptIn(KtorExperimentalLocationsAPI::class)
        @Suppress("unused")
        fun Application.module(testing: Boolean = false) {

            DataBaseFactory.init()
            val db = repo()
            val jwtService = JwtService()
            val hashFunction = { s: String -> hash(s) }



            install(Locations) {
            }

            routing {
                get("/") {
                    call.respondText("HELLO WORLD!", contentType = ContentType.Text.Plain)
                }
                UserRoutes(db, jwtService, hashFunction)


            }

        }
    }.start(wait = true)
}