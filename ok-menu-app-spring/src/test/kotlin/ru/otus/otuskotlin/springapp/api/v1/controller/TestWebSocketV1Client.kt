package ru.otus.otuskotlin.springapp.api.v1.controller

import kotlinx.coroutines.delay
import jakarta.websocket.*

@Suppress("unused", "UNUSED_PARAMETER")
@ClientEndpoint
class TestWebSocketV1Client {
    var session: Session? = null
    private val messages: MutableList<String> = mutableListOf()

    @OnOpen
    fun onOpen(session: Session?) {
        this.session = session
    }

    @OnClose
    fun onClose(session: Session?, reason: CloseReason) {
        this.session = null
    }

    @OnMessage
    fun onMessage(message: String) {
        messages.add(message)
    }

    suspend fun receive(): String {
        while (messages.isEmpty()) {
            delay(100)
        }
        return messages.removeFirst()
    }
}