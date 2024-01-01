package ru.otus.otuskotlin.marketplace.biz

import ru.otus.otuskotlin.MnContext
import ru.otus.otuskotlin.marketplace.stubs.MbizDishStub
import ru.otus.otuskotlin.models.MnCommand
import ru.otus.otuskotlin.models.MnWorkMode

class MbizDishProcessor {
    suspend fun exec(ctx: MnContext) {
        // TODO: Rewrite temporary stub solution with BIZ
        require(ctx.workMode == MnWorkMode.STUB) {
            "Currently working only in STUB mode."
        }

        when (ctx.command) {
            MnCommand.SEARCH -> {
                ctx.dishesResponse.addAll(MbizDishStub.prepareSearchList("Болт"))
            }
            else -> {
                ctx.dishResponse = MbizDishStub.get()
            }
        }
    }
}
