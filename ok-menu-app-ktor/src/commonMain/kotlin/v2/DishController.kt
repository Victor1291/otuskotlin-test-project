package ru.otus.otuskotlin.marketplace.app.ktor.v2

import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import ru.otus.otuskotlin.MnContext
import ru.otus.otuskotlin.api.v2.models.*
import ru.otus.otuskotlin.marketplace.biz.MbizDishProcessor
import ru.otuskotlin.test.mappers.v2.fromTransport
import ru.otuskotlin.test.mappers.v2.*

suspend fun ApplicationCall.createDish(processor: MbizDishProcessor) {
    val request = receive<DishCreateRequest>()
    val context = MnContext()
    context.fromTransport(request)
    processor.exec(context)
    respond(context.toTransportCreate())
}

suspend fun ApplicationCall.readDish(processor: MbizDishProcessor) {
    val request = receive<DishReadRequest>()
    val context = MnContext()
    context.fromTransport(request)
    processor.exec(context)
    respond(context.toTransportRead())
}

suspend fun ApplicationCall.updateDish(processor: MbizDishProcessor) {
    val request = receive<DishUpdateRequest>()
    val context = MnContext()
    context.fromTransport(request)
    processor.exec(context)
    respond(context.toTransportUpdate())
}

suspend fun ApplicationCall.deleteDish(processor: MbizDishProcessor) {
    val request = receive<DishDeleteRequest>()
    val context = MnContext()
    context.fromTransport(request)
    processor.exec(context)
    respond(context.toTransportDelete())
}

suspend fun ApplicationCall.searchDish(processor: MbizDishProcessor) {
    val request = receive<DishSearchRequest>()
    val context = MnContext()
    context.fromTransport(request)
    processor.exec(context)
    respond(context.toTransportSearch())
}
