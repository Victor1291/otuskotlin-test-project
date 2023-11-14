package ru.otus.otuskotlin.oop

import org.junit.Test

class ObjectsExample {
    companion object {
        // init when ObjectsExample will be loaded
        init {
            println("companion inited")
        }

        fun doSmth() {
            println("companion object")
        }
    }

    object A {
        // lazy init whet getting A first time
        init {
            println("A inited")
        }

        fun doSmth() {
            println("object A")
        }
    }
}

class ObjectsTest {
    @Test
    fun test() {
        ObjectsExample()
        ObjectsExample.doSmth()
        ObjectsExample.doSmth()
        ObjectsExample.A.doSmth()
        ObjectsExample.A.doSmth()
    }
}
