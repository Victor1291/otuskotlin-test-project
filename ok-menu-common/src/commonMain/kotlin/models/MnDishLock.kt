package ru.otus.otuskotlin.models

import kotlin.jvm.JvmInline


@JvmInline
value class MnDishLock(private val id: String) {
    fun asString() = id

    companion object {
        val NONE = MnDishLock("")
    }
}