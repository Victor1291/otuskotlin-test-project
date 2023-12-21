package models

import ru.otus.otuskotlin.models.*

/**
 *
 *
 * @param title Название блюда
 * @param description Описание блюда
 * @param price Стоимость блюда
 * @param image фото блюда
 * @param typeDish тип блюда
 * @param visibility
 * @param id Идентификатор объявления
 * @param ownerId Идентификатор пользователя
 * @param lock Версия оптимистичной блокировки
 * @param permissions
 */
data class MnDish (

    var productId: MnProductId = MnProductId.NONE,
    var id: MnDishId = MnDishId.NONE,
    var ownerId: MnUserId = MnUserId.NONE,

    var title: String = "",
    var description: String = "",
    var price: String = "",
    var image: String = "",
    var visibility: MnDishVisibility = MnDishVisibility.NONE,
    var lock: MnDishLock = MnDishLock.NONE,
    val permissions: Set<MnDishPermissionClient>? = mutableSetOf()
) {

    fun isEmpty() = this == NONE
    companion object {
        private val NONE = MnDish()
    }

}