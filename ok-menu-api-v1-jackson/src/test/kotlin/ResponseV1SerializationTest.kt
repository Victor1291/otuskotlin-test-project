package ru.otus.otuskotlin.marketplace.api.v1

import ru.otus.otuskotlin.api.v1.models.*
import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertEquals

class ResponseV1SerializationTest {
    private val response = DishCreateResponse(
        requestId = "123",
        dish = DishResponseObject(
            title = "dish title",
            description = "dish description",
            price = "10$",
            visibility = DishVisibility.PUBLIC,
            image = "picture",
            typeDish = "first dish",
        )
    )

    @Test
    fun serialize() {
        val json = apiV1Mapper.writeValueAsString(response)

        assertContains(json, Regex("\"title\":\\s*\"dish title\""))
        assertContains(json, Regex("\"responseType\":\\s*\"create\""))
    }

    @Test
    fun deserialize() {
        val json = apiV1Mapper.writeValueAsString(response)
        val obj = apiV1Mapper.readValue(json, IResponse::class.java) as DishCreateResponse

        assertEquals(response, obj)
    }
}
