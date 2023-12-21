package ru.otus.otuskotlin.marketplace.app.ktor.v1

import io.ktor.server.application.*
import io.ktor.server.routing.*
import ru.otus.otuskotlin.marketplace.biz.MbizDishProcessor

fun Route.v1Dish(processor: MbizDishProcessor) {
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
