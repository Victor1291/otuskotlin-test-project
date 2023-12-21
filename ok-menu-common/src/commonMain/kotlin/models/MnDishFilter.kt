package ru.otus.otuskotlin.models

data class MnDishFilter(
    var searchString: String = "",
    var ownerId: MnUserId = MnUserId.NONE,
)
