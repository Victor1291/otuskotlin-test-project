package ru.otus.otuskotlin.marketplace.m1l4.lesson
import java.time.LocalDateTime
import org.junit.Test
import kotlin.test.assertEquals

class Trivial {
    @Test
    fun primitive() {
        class SomeClass(
            val x: Int,
            val z: String = "",
            val y: String = "string $x $z"
        )

        val x = java.time.LocalDateTime.MIN.run {
            plusDays(2)
        }

        assertEquals(LocalDateTime.MIN.plusDays(2),x)

    }

}