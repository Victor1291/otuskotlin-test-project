package ru.otus.otuskotlin.marketplace.common.helpers

import ru.otus.otuskotlin.MnContext
import ru.otus.otuskotlin.models.MnError

fun Throwable.asMkplError(
    code: String = "unknown",
    group: String = "exceptions",
    message: String = this.message ?: "",
) = MnError(
    code = code,
    group = group,
    field = "",
    message = message,
    exception = this,
)

fun MnContext.addError(vararg error: MnError) = errors.addAll(error)
