package ru.otus.otuskotlin.markeplace.springapp.api.v1.controller

import org.springframework.web.bind.annotation.*
import ru.otus.otuskotlin.MnContext
import ru.otus.otuskotlin.api.v1.models.*
import ru.otus.otuskotlin.marketplace.biz.MbizDishProcessor
import ru.otuskotlin.test.mappers.v1.*

@Suppress("unused")
@RestController
@RequestMapping("v1/dish")
class DishControllerV1(private val processor: MbizDishProcessor) {

    // v1/ad/create
    @PostMapping("create")
    suspend fun createDish(@RequestBody request: DishCreateRequest): DishCreateResponse {
        val context = MnContext()
        context.fromTransport(request)
        processor.exec(context)
        return context.toTransportCreate()
    }

    @PostMapping("read")
    suspend fun readDish(@RequestBody request: DishReadRequest): DishReadResponse {
        val context = MnContext()
        context.fromTransport(request)
        processor.exec(context)
        return context.toTransportRead()
    }

    @RequestMapping("update", method = [RequestMethod.POST])
    suspend fun updateDish(@RequestBody request: DishUpdateRequest): DishUpdateResponse {
        val context = MnContext()
        context.fromTransport(request)
        processor.exec(context)
        return context.toTransportUpdate()
    }

    @PostMapping("delete")
    suspend fun deleteDish(@RequestBody request: DishDeleteRequest): DishDeleteResponse {
        val context = MnContext()
        context.fromTransport(request)
        processor.exec(context)
        return context.toTransportDelete()
    }

    @PostMapping("search")
    suspend fun searchDish(@RequestBody request: DishSearchRequest): DishSearchResponse {
        val context = MnContext()
        context.fromTransport(request)
        processor.exec(context)
        return context.toTransportSearch()
    }
}
