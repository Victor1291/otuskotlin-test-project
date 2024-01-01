package ru.otus.otuskotlin.springapp.api.v1.ws

import org.springframework.context.annotation.Configuration
import org.springframework.web.socket.config.annotation.EnableWebSocket
import org.springframework.web.socket.config.annotation.WebSocketConfigurer
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry

@Suppress("unused")
@Configuration
@EnableWebSocket
class WebSocketConfigV1(val handlerV1: ru.otus.otuskotlin.springapp.api.v1.ws.WsDishHandlerV1) : WebSocketConfigurer {

    override fun registerWebSocketHandlers(registry: WebSocketHandlerRegistry) {
        registry.addHandler(handlerV1, "/v1/ws").setAllowedOrigins("*")
    }
}