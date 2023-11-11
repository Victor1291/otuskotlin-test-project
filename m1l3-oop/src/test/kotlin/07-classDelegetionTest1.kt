package ru.otus.otuskotlin.oop

interface Base1 {
    fun print()
}

class BaseImpl(val x: Int): Base1 {
    override fun print() {
        print(x)
    }
}
//Мы имплементируем интерфейс , и на вход принимаем наш интерфейс.
//и тогда мы сможем переопределить свойства и методы базового класса
class Derived(b: Base1) : Base1 by b

fun main() {
    val b = BaseImpl(10)
    val c = Derived(b)
    c.print()
}