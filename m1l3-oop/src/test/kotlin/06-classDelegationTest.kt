package ru.otus.otuskotlin.oop

import org.junit.Test
import kotlin.test.assertEquals
//интерфейс с двумя функциями, которые возвращают string
interface IDelegate {
    fun x(): String
    fun y(): String
}

//класс реализующий интерфейс
class MyClass() : IDelegate {
    override fun x(): String {
        println("MyClass.x()")
        return "x"
    }

    override fun y(): String {
        println("MyClass.x()")
        return "y"
    }
}

// Если объявлен интерфейс, и этот интерфейс делегирует какой-то класс
//мы можем создать делегат, вот делегат
class MyDelegate(
    private val del: IDelegate
) : IDelegate by del {
    override fun x(): String {
        println("Calling x")
        val str = del.x()
        println("Calling x done")
        return "delegate for ($str)"
    }
}

internal class ClassDelegationTest {
    @Test
    fun delegate() {
        val base = MyClass()
        val delegate = MyDelegate(base)

        println("Calling base")
        assertEquals("x", base.x())
        assertEquals("y", base.y())
        println("Calling delegate")
        assertEquals("delegate for (x)", delegate.x())
        assertEquals("y", delegate.y())
    }
}

//преимущества :1. нет необходимости наследоваться
// 2. у нас нету привязки к конкретному базовому классу
//мы привязываемся только к интерфейсу
//в данном случае мы не ограничены открытостью , закрытостью
//класса для наследования.
//мы можем взять любой класс, в том числе и закрытый.
//сделать для него делегат и он расширит свои возможности