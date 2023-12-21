package ru.otus.otuskotlin.marketplace.stubs

import models.MnDish
import ru.otus.otuskotlin.marketplace.stubs.MbizDishStubBolts.DISH_TIRAMISU
import ru.otus.otuskotlin.models.MnDishId


object MbizDishStub {
    fun get(): MnDish = DISH_TIRAMISU

    fun prepareResult(block: MnDish.() -> Unit): MnDish = get().apply(block)

    fun prepareSearchList(filter: String) = listOf(
        mnDish("t-01", filter),
        mnDish("t-02", filter),
        mnDish("t-03", filter),
        mnDish("t-04", filter),
        mnDish("t-05", filter),
        mnDish("t-06", filter),
    )

    private fun mnDish(id: String, filter: String) =
        mnDish(DISH_TIRAMISU, id = id, filter = filter)

    private fun mnDish(base: MnDish, id: String, filter: String) = base.copy(
        id = MnDishId(id),
        title = "$filter $id",
        description = "desc $filter $id"
    )

}
