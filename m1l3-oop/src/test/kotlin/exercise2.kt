package ru.otus.otuskotlin.marketplace.m1l3

import kotlin.test.Test
import kotlin.test.assertEquals

interface Dialable {
    fun dial(number: String) : String
}

class Phone : Dialable {
    override fun dial(number: String): String =
        "Dialing $number..."
}

interface Snappable {
    fun takePicture(): String
}

class Camera : Snappable {
    override fun takePicture(): String =
        "Taking picture..."
}

class SmartPhone(
    private val phone: Dialable = Phone(),
    private val camera: Snappable = Camera()
) : Dialable by phone, Snappable by camera

//классы Phone и Camera могут иметь много функций , но доступны только те , что объявлены в интерфейсах.exer

internal class SmartPhoneTest {
    private val smartPhone: SmartPhone = SmartPhone()

    @Test
    fun `Dialing delegates to internal phone`() {
        assertEquals("Dialing 555-1234...", smartPhone.dial("555-1234"))
    }

    @Test
    fun `Taking picture delegates to internal camera`() {
        assertEquals("Taking picture...", smartPhone.takePicture())
    }
}