package ru.otus.otuskotlin.m1l7

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
actual class UserService actual constructor() {
    actual fun serve(user: User): String = "JVM Service for User $user"
}