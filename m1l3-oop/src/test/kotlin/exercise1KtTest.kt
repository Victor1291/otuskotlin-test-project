package ru.otus.otuskotlin.marketplace.m1l3

import java.util.Objects
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNotEquals

data class Rectangle(val width: Int, val height: Int): Figure {

    override fun area() : Int {
        return width * height
    }

    override fun toString(): String {
        return "Rectangle(${width}x$height)"
    }

    override fun equals(other: Any?): Boolean {

        if(this === other) return true
        if(other !is Rectangle ) return false
        return width == other.width && height == other.height
    }

    override fun hashCode(): Int {
        return Objects.hash(width,height)
    }
}

data class Square(val x: Int) : Figure {
    override fun area(): Int {
        return x * x
    }

    override fun toString(): String {
        return "Square($x)"
    }

    override fun equals(other: Any?): Boolean {
//проверка по ссылке на один и тот же объект.
        if(this === other) return true
        //проверка что объект принадлежит классу Square
        if(other !is Square ) return false
        // сравнениваем поля у двух объектов класса Квадрат.
        return x == other.x
    }

    override fun hashCode(): Int {
        return Objects.hash(x)
    }
}

interface Figure {
    fun area(): Int
}

fun diffArea(a: Figure,b: Figure ) : Int{
    return a.area() - b.area()
}


class exercise1KtTest {
    // task 1 - make a Rectangle class that will have width and height
    // as well as the area calculation method - area()
    // the test below should pass - uncomment the code in it
    @Test
    fun rectangleArea() {
        val r = Rectangle(10, 20)
        assertEquals(200, r.area())
        assertEquals(10, r.width)
        assertEquals(20, r.height)
    }

    // task 2 - make the Rectangle.toString() method
    // the test below should pass - uncomment the code in it
    @Test
    fun rectangleToString() {
        val r = Rectangle(10, 20)
        assertEquals("Rectangle(10x20)", r.toString())
    }

    // task 3 - make Rectangle.equals() and Rectangle.hashCode() methods
    // the test below should pass - uncomment the code in it
    @Test
    fun rectangleEquals() {
        val a = Rectangle(10, 20)
        val b = Rectangle(10, 20)
        val c = Rectangle(20, 10)
        assertEquals(a, b)
        assertEquals(a.hashCode(), b.hashCode())
        assertFalse (a === b)
        assertNotEquals(a, c)
    }

    // task 4 - make the Square class
    // the test below should pass - uncomment the code in it
    @Test
    fun squareEquals() {
        val a = Square(10)
        val b = Square(10)
        val c = Square(20)
        assertEquals(a, b)
        assertEquals(a.hashCode(), b.hashCode())
        assertFalse (a === b)
        assertNotEquals(a, c)
        println(a)
    }

    // task 5 - make the Figure interface with the area() method, inherit Rectangle and Square from it
    // the test below should pass - uncomment the code in it
    @Test
    fun figureArea() {
        var f : Figure = Rectangle(10, 20)
        assertEquals(f.area(), 200)

        f = Square(10)
        assertEquals(f.area(), 100)
    }

    // task 6 - make the diffArea(a, b) method
    // the test below should pass - uncomment the code in it
    @Test
    fun diffArea() {
        val a = Rectangle(10, 20)
        val b = Square(10)
        assertEquals(diffArea(a, b), 100)
    }

}
