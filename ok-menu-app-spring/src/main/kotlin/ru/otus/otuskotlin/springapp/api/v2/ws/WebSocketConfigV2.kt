package ru.otus.otuskotlin.springapp.api.v2.ws

import org.springframework.context.annotation.Configuration
import org.springframework.web.socket.config.annotation.EnableWebSocket
import org.springframework.web.socket.config.annotation.WebSocketConfigurer
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry


@Suppress("unused")
@Configuration
@EnableWebSocket
class WebSocketConfigV2(val handlerV2: WsDishHandlerV2) : WebSocketConfigurer {

    override fun registerWebSocketHandlers(registry: WebSocketHandlerRegistry) {
        registry.addHandler(handlerV2, "/v2/ws").setAllowedOrigins("*")
    }
}