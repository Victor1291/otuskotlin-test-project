package ru.otus.otuskotlin.m1l7

import kotlinx.coroutines.delay

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
actual class UserLongService actual constructor() {
    actual suspend fun serve(user: User): Pair<String, User> {
        delay(3000)
        return "Native" to user
    }
}
