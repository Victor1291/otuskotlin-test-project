package ru.otus.otuskotlin.marketplace.api.v2

import kotlinx.serialization.encodeToString
import ru.otus.otuskotlin.api.v2.models.*
import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertEquals

class ResponseV2SerializationTest {
    private val response: IResponse = DishCreateResponse(
        requestId = "123",
        dish = DishResponseObject(
            title = "dish title",
            description = "dish description",
            price = "10$",
            visibility = DishVisibility.PUBLIC,
            image = "picture",
        )
    )

    @Test
    fun serialize() {
//        val json = apiV2Mapper.encodeToString(AdRequestSerializer1, request)
//        val json = apiV2Mapper.encodeToString(RequestSerializers.create, request)
        val json = apiV2Mapper.encodeToString(response)

        println(json)

        assertContains(json, Regex("\"title\":\\s*\"dish title\""))
        assertContains(json, Regex("\"responseType\":\\s*\"create\""))
    }

    @Test
    fun deserialize() {
        val json = apiV2Mapper.encodeToString(response)
        val obj = apiV2Mapper.decodeFromString(json) as DishCreateResponse

        assertEquals(response, obj)
    }
}
