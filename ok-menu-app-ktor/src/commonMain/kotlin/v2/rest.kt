package ru.otus.otuskotlin.marketplace.app.ktor.v2

import io.ktor.server.application.*
import io.ktor.server.routing.*
import ru.otus.otuskotlin.marketplace.biz.MbizDishProcessor

fun Route.v2Ad(processor: MbizDishProcessor) {
    route("dish") {
        post("create") {
            call.createDish(processor)
        }
        post("read") {
            call.readDish(processor)
        }
        post("update") {
            call.updateDish(processor)
        }
        post("delete") {
            call.deleteDish(processor)
        }
        post("search") {
            call.searchDish(processor)
        }
    }
}
