package ru.otus.otuskotlin.springapp.api.v1.controller

import io.kotest.common.runBlocking
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import kotlinx.coroutines.withTimeout
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.server.LocalServerPort
import ru.otus.otuskotlin.api.v1.models.DishInitResponse
import ru.otus.otuskotlin.api.v1.models.IResponse
import ru.otus.otuskotlin.api.v1.models.ResponseResult
import ru.otus.otuskotlin.marketplace.api.v1.apiV1Mapper
import java.net.URI
import jakarta.websocket.ContainerProvider
import jakarta.websocket.WebSocketContainer

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class WsControllerV1Test {

    @LocalServerPort
    private var port: Int = 0

    private lateinit var container: WebSocketContainer
    private lateinit var client: TestWebSocketV1Client

    @BeforeEach
    fun setup() {
        container = ContainerProvider.getWebSocketContainer()
        client = TestWebSocketV1Client()
    }

    @Test
    fun initSession() {
        runBlocking {
            withContext(Dispatchers.IO) {
                container.connectToServer(client, URI.create("ws://localhost:${port}/v1/ws"))
            }

            withTimeout(3000) {
                while (client.session?.isOpen != true) {
                    delay(100)
                }
            }
            assert(client.session?.isOpen == true)
            withTimeout(3000) {
                val incame = client.receive()
                val message = apiV1Mapper.readValue(incame, IResponse::class.java)
                assert(message is DishInitResponse)
                assert(message.result == ResponseResult.SUCCESS)
            }
        }
    }
}