package ru.otus.otuskotlin.models

import kotlin.jvm.JvmInline

enum class MnDishVisibility {
    NONE,
    VISIBLE_TO_OWNER,
    VISIBLE_TO_GROUP,
    VISIBLE_PUBLIC,
}