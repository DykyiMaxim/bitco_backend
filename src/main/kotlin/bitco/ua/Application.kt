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
import io.ktor.auth.jwt.*
import io.ktor.gson.*
import io.ktor.features.*
import io.ktor.locations.*
import io.ktor.server.cio.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import routes.UserLoginRoute
import routes.UserRoutes




fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused")
@kotlin.jvm.JvmOverloads
fun Application.module(testing: Boolean = false) {

    DataBaseFactory.init()
    val db = repo()
    val jwtService = JwtService()
    val hashFunction = { s:String -> hash(s) }



    install(Authentication) {

        jwt("jwt") {



        }

    }

    install(Locations)

    install(ContentNegotiation) {
        gson {
        }
    }

    routing {
        UserRoutes(db,jwtService,hashFunction)




    }
}