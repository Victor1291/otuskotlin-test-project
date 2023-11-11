package ru.otus.otuskotlin.marketplace.m1l4.lesson
import org.junit.Test
import kotlin.test.assertEquals

class KotlinBuilder {

    @Test
    fun x() {
        val s = some {
            x = "one"
            y = "two"
            z = "tree"
        }.build()

        assertEquals("one", s.x)
    }

    private fun some(function: SomeDsl.() -> Unit): SomeDsl = SomeDsl().apply(function)

}

class SomeDsl(
    var x: String = "",
    var y: String = "",
    var z: String = "",
) {
    fun build(): Some = Some(x, y, z)
}
