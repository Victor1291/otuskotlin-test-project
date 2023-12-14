package ru.otus.otuskotlin

import kotlinx.datetime.Instant
import models.MnDish
import ru.otus.otuskotlin.models.*
import ru.otus.otuskotlin.stubs.MnStubs

data class MnContext(
    var command: MnCommand = MnCommand.NONE,
    var state: MnState = MnState.NONE,
    val errors: MutableList<MnError> = mutableListOf(),

    var workMode: MnWorkMode = MnWorkMode.PROD,
    var stubCase: MnStubs = MnStubs.NONE,

    var requestId: MnRequestId = MnRequestId.NONE,
    var timeStart: Instant = Instant.NONE,
    var dishRequest: MnDish = MnDish(),
    var dishFilterRequest: MnDishFilter = MnDishFilter(),
    var dishResponse: MnDish = MnDish(),
    var dishesResponse: MutableList<MnDish> = mutableListOf(),
)
