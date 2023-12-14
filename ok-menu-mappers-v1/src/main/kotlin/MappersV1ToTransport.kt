package ru.otuskotlin.test.mappers.v1

import models.MnDish
import ru.otus.otuskotlin.MnContext
import ru.otus.otuskotlin.api.v1.models.*
import ru.otus.otuskotlin.models.*
import ru.otuskotlin.test.mappers.v1.exceptions.UnknownMnCommand

fun MnContext.toTransportDish(): IResponse = when (val cmd = command) {
    MnCommand.CREATE -> toTransportCreate()
    MnCommand.READ -> toTransportRead()
    MnCommand.UPDATE -> toTransportUpdate()
    MnCommand.DELETE -> toTransportDelete()
    MnCommand.SEARCH -> toTransportSearch()
    MnCommand.OFFERS -> toTransportOffers()
    MnCommand.NONE -> throw UnknownMnCommand(cmd)
}

fun MnContext.toTransportCreate() = DishCreateResponse(
    requestId = this.requestId.asString().takeIf { it.isNotBlank() },
    result = if (state == MnState.RUNNING) ResponseResult.SUCCESS else ResponseResult.ERROR,
    errors = errors.toTransportErrors(),
    dish = dishResponse.toTransportDish()
)

fun MnContext.toTransportRead() = DishReadResponse(
    requestId = this.requestId.asString().takeIf { it.isNotBlank() },
    result = if (state == MnState.RUNNING) ResponseResult.SUCCESS else ResponseResult.ERROR,
    errors = errors.toTransportErrors(),
    dish = dishResponse.toTransportDish()
)

fun MnContext.toTransportUpdate() = DishUpdateResponse(
    requestId = this.requestId.asString().takeIf { it.isNotBlank() },
    result = if (state == MnState.RUNNING) ResponseResult.SUCCESS else ResponseResult.ERROR,
    errors = errors.toTransportErrors(),
    dish = dishResponse.toTransportDish()
)

fun MnContext.toTransportDelete() = DishDeleteResponse(
    requestId = this.requestId.asString().takeIf { it.isNotBlank() },
    result = if (state == MnState.RUNNING) ResponseResult.SUCCESS else ResponseResult.ERROR,
    errors = errors.toTransportErrors(),
    dish = dishResponse.toTransportDish()
)

fun MnContext.toTransportSearch() = DishSearchResponse(
    requestId = this.requestId.asString().takeIf { it.isNotBlank() },
    result = if (state == MnState.RUNNING) ResponseResult.SUCCESS else ResponseResult.ERROR,
    errors = errors.toTransportErrors(),
    dishes = dishesResponse.toTransportDish()
)

fun MnContext.toTransportOffers() = DishOffersResponse(
    requestId = this.requestId.asString().takeIf { it.isNotBlank() },
    result = if (state == MnState.RUNNING) ResponseResult.SUCCESS else ResponseResult.ERROR,
    errors = errors.toTransportErrors(),
    dishes = dishesResponse.toTransportDish()
)

fun List<MnDish>.toTransportDish(): List<DishResponseObject>? = this
    .map { it.toTransportDish() }
    .toList()
    .takeIf { it.isNotEmpty() }

fun MnContext.toTransportInit() = DishInitResponse(
    requestId = this.requestId.asString().takeIf { it.isNotBlank() },
    result = if (errors.isEmpty()) ResponseResult.SUCCESS else ResponseResult.ERROR,
    errors = errors.toTransportErrors(),
)

private fun MnDish.toTransportDish(): DishResponseObject = DishResponseObject(
    id = id.takeIf { it != MnDishId.NONE }?.asString(),
    title = title.takeIf { it.isNotBlank() },
    description = description.takeIf { it.isNotBlank() },
    price = price.takeIf { it.isNotBlank() },
    image = image.takeIf { it.isNotBlank() },
    ownerId = ownerId.takeIf { it != MnUserId.NONE }?.asString(),
    visibility = visibility.toTransportDish(),
    permissions = permissions?.toTransportDish(),
)

private fun Set<MnDishPermissionClient>.toTransportDish(): Set<DishPermissions>? = this
    .map { it.toTransportDish() }
    .toSet()
    .takeIf { it.isNotEmpty() }

private fun MnDishPermissionClient.toTransportDish() = when (this) {
    MnDishPermissionClient.READ -> DishPermissions.READ
    MnDishPermissionClient.UPDATE -> DishPermissions.UPDATE
    MnDishPermissionClient.MAKE_VISIBLE_OWNER -> DishPermissions.MAKE_VISIBLE_OWNER
    MnDishPermissionClient.MAKE_VISIBLE_GROUP -> DishPermissions.MAKE_VISIBLE_GROUP
    MnDishPermissionClient.MAKE_VISIBLE_PUBLIC -> DishPermissions.MAKE_VISIBLE_PUBLIC
    MnDishPermissionClient.DELETE -> DishPermissions.DELETE
}

private fun MnDishVisibility.toTransportDish(): DishVisibility? = when (this) {
    MnDishVisibility.VISIBLE_PUBLIC -> DishVisibility.PUBLIC
    MnDishVisibility.VISIBLE_TO_GROUP -> DishVisibility.REGISTERED_ONLY
    MnDishVisibility.VISIBLE_TO_OWNER -> DishVisibility.OWNER_ONLY
    MnDishVisibility.NONE -> null
}

private fun List<MnError>.toTransportErrors(): List<Error>? = this
    .map { it.toTransportDish() }
    .toList()
    .takeIf { it.isNotEmpty() }

private fun MnError.toTransportDish() = Error(
    code = code.takeIf { it.isNotBlank() },
    group = group.takeIf { it.isNotBlank() },
    field = field.takeIf { it.isNotBlank() },
    message = message.takeIf { it.isNotBlank() },
)
