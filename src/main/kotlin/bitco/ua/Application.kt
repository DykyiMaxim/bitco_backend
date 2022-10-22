package bitco.ua

import authentication.JwtService
import authentication.hash
import bitco.ua.reposotory.DataBaseFactory
import bitco.ua.reposotory.repo
import io.ktor.application.*
import io.ktor.response.*
import io.ktor.request.*
import io.ktor.routing.*
import io.ktor.http.*
import io.ktor.sessions.*
import io.ktor.auth.*
import io.ktor.gson.*
import io.ktor.features.*
import io.ktor.locations.*
import io.ktor.server.cio.*
import io.ktor.server.engine.*
import routes.UserLoginRoute
import routes.UserRoutes


fun main() {
    embeddedServer(CIO, port = 8080, host = "127.163.0.1") {
        DataBaseFactory.init()
        val db=repo()
        val jwtService = JwtService()
        val hashFunction = {s:String ->hash(s)}
        install(Authentication) {}
        install(Locations)
        install(ContentNegotiation) {
            gson {}
        }
        install(Routing){
            UserRoutes(db,jwtService,hashFunction)
        }


    }.start(wait = true)
}