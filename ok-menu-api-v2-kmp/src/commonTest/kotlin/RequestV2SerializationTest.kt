package ru.otus.otuskotlin.marketplace.api.v2

import kotlinx.serialization.encodeToString
import ru.otus.otuskotlin.api.v2.apiV2Mapper
import ru.otus.otuskotlin.api.v2.models.*
import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertEquals

class RequestV2SerializationTest {
    private val request: IRequest = DishCreateRequest(
        requestId = "123",
        debug = DishDebug(
            mode = DishRequestDebugMode.STUB,
            stub = DishRequestDebugStubs.BAD_TITLE
        ),
        dish = DishCreateObject(
            title = "dish title",
            description = "dish description",
            price = "10$",
            visibility = DishVisibility.PUBLIC,
            image = "picture",
        )
    )

    @Test
    fun serialize() {
        val json = apiV2Mapper.encodeToString(IRequest.serializer(), request)

        println(json)

        assertContains(json, Regex("\"title\":\\s*\"dish title\""))
        assertContains(json, Regex("\"mode\":\\s*\"stub\""))
        assertContains(json, Regex("\"stub\":\\s*\"badTitle\""))
        assertContains(json, Regex("\"requestType\":\\s*\"create\""))
    }

    @Test
    fun deserialize() {
        val json = apiV2Mapper.encodeToString(request)
        val obj = apiV2Mapper.decodeFromString(json) as DishCreateRequest

        assertEquals(request, obj)
    }
    @Test
    fun deserializeNaked() {
        val jsonString = """
            {"requestId": "123"}
        """.trimIndent()
        val obj = apiV2Mapper.decodeFromString<DishCreateRequest>(jsonString)

        assertEquals("123", obj.requestId)
    }
}
