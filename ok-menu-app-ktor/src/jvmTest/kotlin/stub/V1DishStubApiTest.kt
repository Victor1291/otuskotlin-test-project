package ru.otus.otuskotlin.marketplace.app.ktor.stub

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.SerializationFeature
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.jackson.*
import io.ktor.server.testing.*
import ru.otus.otuskotlin.api.v1.models.*
import ru.otus.otuskotlin.marketplace.app.ktor.moduleJvm
import kotlin.test.Test
import kotlin.test.assertEquals

class V1DishStubApiTest {
    @Test
    fun create() = v1TestApplication { client ->
        val response = client.post("/v1/dish/create") {
            val requestObj = DishCreateRequest(
                requestId = "12345",
                dish = DishCreateObject(
                    title = "торт Черепаха",
                    description = "Очень вкусный сметаный торт. Покрытый шоколадом.",
                    image = "foto of cake",
                    price = "10$",
                    visibility = DishVisibility.PUBLIC,
                ),
                debug = DishDebug(
                    mode = DishRequestDebugMode.STUB,
                    stub = DishRequestDebugStubs.SUCCESS
                )
            )
            contentType(ContentType.Application.Json)
            setBody(requestObj)
        }
        val responseObj = response.body<DishCreateResponse>()
        assertEquals(200, response.status.value)
        assertEquals("666", responseObj.dish?.id)
    }


    @Test
    fun read() = v1TestApplication { client ->
        val response = client.post("/v1/dish/read") {
            val requestObj = DishReadRequest(
                requestId = "12345",
                dish = DishReadObject("666"),
                debug = DishDebug(
                    mode = DishRequestDebugMode.STUB,
                    stub = DishRequestDebugStubs.SUCCESS
                )
            )
            contentType(ContentType.Application.Json)
            setBody(requestObj)
        }
        val responseObj = response.body<DishReadResponse>()
        assertEquals(200, response.status.value)
        assertEquals("666", responseObj.dish?.id)
    }

    @Test
    fun update() = v1TestApplication { client ->
        val response = client.post("/v1/dish/update") {
            val requestObj = DishUpdateRequest(
                requestId = "12345",
                dish = DishUpdateObject(
                    title = "торт Черепаха",
                    description = "Очень вкусный сметаный торт. Покрытый шоколадом.",
                    image = "foto of cake",
                    price = "10$",
                    visibility = DishVisibility.PUBLIC,
                ),
                debug = DishDebug(
                    mode = DishRequestDebugMode.STUB,
                    stub = DishRequestDebugStubs.SUCCESS
                )
            )
            contentType(ContentType.Application.Json)
            setBody(requestObj)
        }
        val responseObj = response.body<DishUpdateResponse>()
        assertEquals(200, response.status.value)
        assertEquals("666", responseObj.dish?.id)
    }

    @Test
    fun delete() = v1TestApplication { client ->
        val response = client.post("/v1/dish/delete") {
            val requestObj = DishDeleteRequest(
                requestId = "12345",
                dish = DishDeleteObject(
                    id = "666",
                ),
                debug = DishDebug(
                    mode = DishRequestDebugMode.STUB,
                    stub = DishRequestDebugStubs.SUCCESS
                )
            )
            contentType(ContentType.Application.Json)
            setBody(requestObj)
        }
        val responseObj = response.body<DishDeleteResponse>()
        assertEquals(200, response.status.value)
        assertEquals("666", responseObj.dish?.id)
    }

    @Test
    fun search() = v1TestApplication { client ->
        val response = client.post("/v1/dish/search") {
            val requestObj = DishSearchRequest(
                requestId = "12345",
                dishFilter = DishSearchFilter(),
                debug = DishDebug(
                    mode = DishRequestDebugMode.STUB,
                    stub = DishRequestDebugStubs.SUCCESS
                )
            )
            contentType(ContentType.Application.Json)
            setBody(requestObj)
        }
        val responseObj = response.body<DishSearchResponse>()
        assertEquals(200, response.status.value)
        assertEquals("d-666-01", responseObj.dishes?.first()?.id)
    }

    private fun v1TestApplication(function: suspend (HttpClient) -> Unit): Unit = testApplication {
        application { moduleJvm() }
        val client = createClient {
            install(ContentNegotiation) {
                jackson {
                    disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)

                    enable(SerializationFeature.INDENT_OUTPUT)
                    writerWithDefaultPrettyPrinter()
                }
            }
        }
        function(client)
    }
}
