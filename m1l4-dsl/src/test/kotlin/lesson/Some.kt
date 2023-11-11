package ru.otus.otuskotlin.marketplace.m1l4.lesson

import org.junit.Test

class Some (
    var x: String = "",
    var y: String = "",
    var z: String = ""
){


    class Builder {
        private var _x: String = ""
        private var _y: String = ""
        private var _z: String = ""

        fun build(): Some {
            val s = Some()
            s.x = _x
            s.y = _y
            s.z = _z
            return s
        }

        fun setX(x: String): Builder {
            _x = x
            return this
        }

    }
}

class SomeTest {
    @Test
    fun someTest() {
        Some.Builder()
            .setX("one")
            .setX("one")
            .setX("one")
            .build()
    }
}