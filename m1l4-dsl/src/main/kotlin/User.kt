package ru.otus.otuskotlin.m1l4.userlesson

import java.time.LocalDate

data class User (
    val fName: String,
    val lName: String,
    val mName: String,
    val dob: LocalDate,
    val pob: String,
    val operations: Set<UserOps>
)

enum class UserOps {
    READ,
    DELETE,
    UPDATE,
    CREATE,
    SEARCH
}
