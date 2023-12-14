package ru.otus.otuskotlin.models

import kotlin.jvm.JvmInline

@JvmInline
value class MnUserId (private val id: String) {
    fun asString() = id

    companion object {
        val NONE = MnUserId("")
    }
}