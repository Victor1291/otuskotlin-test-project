package ru.otus.otuskotlin.marketplace.stubs

import models.*
import ru.otus.otuskotlin.models.*

object MbizDishStubBolts {
    val DISH_TIRAMISU: MnDish
        get() = MnDish(
            id = MnDishId("666"),
            title = "торт Тирамису",
            description = "торт с кофейной начинкой, с ароматом коньяка.",
            price = "700 rub.",
            image = "picture",
            ownerId = MnUserId("user-1"),
            visibility = MnDishVisibility.VISIBLE_PUBLIC,
            permissions = mutableSetOf(
                MnDishPermissionClient.READ,
                MnDishPermissionClient.UPDATE,
                MnDishPermissionClient.DELETE,
                MnDishPermissionClient.MAKE_VISIBLE_PUBLIC,
                MnDishPermissionClient.MAKE_VISIBLE_GROUP,
                MnDishPermissionClient.MAKE_VISIBLE_OWNER,
            )
        )
}
