package ru.otuskotlin.test.mappers.v2

import exceptions.UnknownRequestClass
import models.MnDish
import ru.otus.otuskotlin.MnContext
import ru.otus.otuskotlin.api.v2.models.*
import ru.otus.otuskotlin.api.v2.models.DishRequestDebugStubs.*
import ru.otus.otuskotlin.models.*
import ru.otus.otuskotlin.stubs.MnStubs

fun MnContext.fromTransport(request: IRequest) = when (request) {
    is DishCreateRequest -> fromTransport(request)
    is DishReadRequest -> fromTransport(request)
    is DishUpdateRequest -> fromTransport(request)
    is DishDeleteRequest -> fromTransport(request)
    is DishSearchRequest -> fromTransport(request)
    else -> throw UnknownRequestClass(request)
}

private fun String?.toDishId() = this?.let { MnDishId(it) } ?: MnDishId.NONE
private fun String?.toDishWithId() = MnDish(id = this.toDishId())
private fun IRequest?.requestId() = this?.requestId?.let { MnRequestId(it) } ?: MnRequestId.NONE

private fun DishDebug?.transportToWorkMode(): MnWorkMode = when (this?.mode) {
    DishRequestDebugMode.PROD -> MnWorkMode.PROD
    DishRequestDebugMode.TEST -> MnWorkMode.TEST
    DishRequestDebugMode.STUB -> MnWorkMode.STUB
    null -> MnWorkMode.PROD
}

private fun DishDebug?.transportToStubCase(): MnStubs = when (this?.stub) {
    SUCCESS -> MnStubs.SUCCESS
    NOT_FOUND -> MnStubs.NOT_FOUND
    BAD_ID -> MnStubs.BAD_ID
    BAD_TITLE -> MnStubs.BAD_TITLE
    BAD_PRICE -> MnStubs.BAD_PRICE
    BAD_IMAGE -> MnStubs.BAD_IMAGE
    BAD_DESCRIPTION -> MnStubs.BAD_DESCRIPTION
    BAD_VISIBILITY -> MnStubs.BAD_VISIBILITY
    CANNOT_DELETE -> MnStubs.CANNOT_DELETE
    BAD_SEARCH_STRING -> MnStubs.BAD_SEARCH_STRING
    null -> MnStubs.NONE

}

fun MnContext.fromTransport(request: DishCreateRequest) {
    command = MnCommand.CREATE
    requestId = request.requestId()
    dishRequest = request.dish?.toInternal() ?: MnDish()
    workMode = request.debug.transportToWorkMode()
    stubCase = request.debug.transportToStubCase()
}

fun MnContext.fromTransport(request: DishReadRequest) {
    command = MnCommand.READ
    requestId = request.requestId()
    dishRequest = request.dish?.id.toDishWithId()
    workMode = request.debug.transportToWorkMode()
    stubCase = request.debug.transportToStubCase()
}

fun MnContext.fromTransport(request: DishUpdateRequest) {
    command = MnCommand.UPDATE
    requestId = request.requestId()
    dishRequest = request.dish?.toInternal() ?: MnDish()
    workMode = request.debug.transportToWorkMode()
    stubCase = request.debug.transportToStubCase()
}

fun MnContext.fromTransport(request: DishDeleteRequest) {
    command = MnCommand.DELETE
    requestId = request.requestId()
    dishRequest = request.dish?.id.toDishWithId()
    workMode = request.debug.transportToWorkMode()
    stubCase = request.debug.transportToStubCase()
}

fun MnContext.fromTransport(request: DishSearchRequest) {
    command = MnCommand.SEARCH
    requestId = request.requestId()
    dishFilterRequest = request.dishFilter.toInternal()
    workMode = request.debug.transportToWorkMode()
    stubCase = request.debug.transportToStubCase()
}

private fun DishSearchFilter?.toInternal(): MnDishFilter = MnDishFilter(
    searchString = this?.searchString ?: ""
)

private fun DishCreateObject.toInternal(): MnDish = MnDish(
    title = this.title ?: "",
    description = this.description ?: "",
    price = this.price ?: "",
    image = this.image ?: "",
    visibility = this.visibility.fromTransport(),
)

private fun DishUpdateObject.toInternal(): MnDish = MnDish(
    id = this.id.toDishId(),
    title = this.title ?: "",
    description = this.description ?: "",
    price = this.price ?: "",
    image = this.image ?: "",
    visibility = this.visibility.fromTransport(),
)

private fun DishVisibility?.fromTransport(): MnDishVisibility = when (this) {
    DishVisibility.PUBLIC -> MnDishVisibility.VISIBLE_PUBLIC
    DishVisibility.OWNER_ONLY -> MnDishVisibility.VISIBLE_TO_OWNER
    DishVisibility.REGISTERED_ONLY -> MnDishVisibility.VISIBLE_TO_GROUP
    null -> MnDishVisibility.NONE
}