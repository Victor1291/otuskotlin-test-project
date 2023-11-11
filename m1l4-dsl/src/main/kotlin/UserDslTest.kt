package ru.otus.otuskotlin.m1l4.userlesson


import java.time.LocalDate

class UserDslTest {

    fun userTest() {
        val user = buildUser2 {
            name {
                first2 = "Ivan"
                last2 = "Petrov"
                middle = "Semenovich"
            }
            birth {
                age(19)
                date(LocalDate.of(2000, 1, 1))
                date("2000-01-01")
                place = "Камчатка"
            }
             allowed {
                 add(UserOps.CREATE)
                 add("UPDATE")
                 +UserOps.DELETE
             }

        }
    }

    private fun buildUser2(function: UserDsl2.() -> Unit): User = UserDsl2().apply(function).build()
}

private fun UserBirthDsl.age(_age: Int) {
    date(LocalDate.now().minusYears(_age.toLong()))
}

private fun UserBirthDsl.date(dt: String) {
    this.date(LocalDate.parse(dt))
}

//описываем блоки
class UserDsl2 {
    fun name(block: UserNameDsl.() -> Unit) {
        val names = UserNameDsl().apply(block)
    }

    fun birth(block: UserBirthDsl.() -> Unit) {
        val names = UserBirthDsl().apply(block)
    }

    fun build(): User {
        return User(
            "ivan",
            "Petrov",
            "Yurievich",
            LocalDate.now(),
            "Paris",
            setOf(UserOps.CREATE)
        )
    }

    fun allowed(block: UserAllowedDsl.() -> Unit) {

    }


}

class UserAllowedDsl(
    private val permissions: MutableSet<UserOps> = mutableSetOf()
) {

    fun add(op: UserOps) {
        permissions.add(op)
    }

    fun add(op: String) {
        add(UserOps.valueOf(op))
    }

    operator fun UserOps.unaryPlus() {
        add(this)
    }

}

class UserBirthDsl(
    private var _dob: LocalDate = LocalDate.MIN,
    var place: String = "",
) {

    fun date(dt: LocalDate) {
        _dob = dt
    }

}

class UserNameDsl {
    var first2: String = ""
    var last2: String = ""
    var middle: String = ""

}
