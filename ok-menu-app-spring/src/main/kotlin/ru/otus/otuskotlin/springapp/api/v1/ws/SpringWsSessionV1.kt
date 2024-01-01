package ru.otus.otuskotlin.springapp.api.v1.ws

import org.springframework.web.socket.TextMessage
import org.springframework.web.socket.WebSocketSession
import ru.otus.otuskotlin.api.v1.models.IResponse
import ru.otus.otuskotlin.marketplace.api.v1.apiV1ResponseSerialize
import ru.otus.otuskotlin.ws.IMnWsSession

data class SpringWsSessionV1(
    private val session: WebSocketSession,
) : IMnWsSession {
    override suspend fun <T> send(obj: T) {
        require(obj is IResponse)
        val message = apiV1ResponseSerialize(obj)
        session.sendMessage(TextMessage(message))
    }
}