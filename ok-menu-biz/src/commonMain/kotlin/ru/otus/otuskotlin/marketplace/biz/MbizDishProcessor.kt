package ru.otus.otuskotlin.marketplace.biz

import ru.otus.otuskotlin.MnContext
import ru.otus.otuskotlin.marketplace.stubs.MbizDishStub

class MbizDishProcessor {
    suspend fun exec(ctx: MnContext) {
        ctx.dishResponse = MbizDishStub.get()
    }
}
