package ru.otus.otuskotlin.marketplace.app.ktor

import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.plugins.cors.routing.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import ru.otus.otuskotlin.marketplace.api.v2.apiV2Mapper
import ru.otus.otuskotlin.marketplace.app.ktor.v2.v2Dish
import ru.otus.otuskotlin.marketplace.biz.MbizDishProcessor

fun main(args: Array<String>) = io.ktor.server.cio.EngineMain.main(args)

fun Application.module(processor: MbizDishProcessor = MbizDishProcessor()) {
    install(CORS) {
        allowMethod(HttpMethod.Options)
        allowMethod(HttpMethod.Put)
        allowMethod(HttpMethod.Delete)
        allowMethod(HttpMethod.Patch)
        allowHeader(HttpHeaders.Authorization)
        allowHeader("MyCustomHeader")
        allowCredentials = true
        anyHost() // TODO remove
    }

    routing {
        get("/") {
            call.respondText("Hello, Victor!")
        }
        route("v2") {
            install(ContentNegotiation) {
                json(apiV2Mapper)
            }
            v2Dish(processor)
        }
    }
}
