package ru.otus.otuskotlin.marketplace.api.v1

import ru.otus.otuskotlin.api.v1.models.*
import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertEquals

class RequestV1SerializationTest {
    private val request = DishCreateRequest(
        requestId = "123",
        debug = DishDebug(
            mode = DishRequestDebugMode.STUB,
            stub = DishRequestDebugStubs.BAD_TITLE
        ),
        dish = DishCreateObject(
            title = "dish title",
            description = "dish description",
            visibility = DishVisibility.PUBLIC,
        )
    )

    @Test
    fun serialize() {
        val json = apiV1Mapper.writeValueAsString(request)

        assertContains(json, Regex("\"title\":\\s*\"dish title\""))
        assertContains(json, Regex("\"mode\":\\s*\"stub\""))
        assertContains(json, Regex("\"stub\":\\s*\"badTitle\""))
        assertContains(json, Regex("\"requestType\":\\s*\"create\""))
    }

    @Test
    fun deserialize() {
        val json = apiV1Mapper.writeValueAsString(request)
        val obj = apiV1Mapper.readValue(json, IRequest::class.java) as DishCreateRequest

        assertEquals(request, obj)
    }

    @Test
    fun deserializeNaked() {
        val jsonString = """
            {"requestId": "123"}
        """.trimIndent()
        val obj = apiV1Mapper.readValue(jsonString, DishCreateRequest::class.java)

        assertEquals("123", obj.requestId)
    }
}
