package ru.otus.otuskotlin.springapp.api.v2.ws

import helpers.asMnError
import kotlinx.coroutines.runBlocking
import kotlinx.datetime.Clock
import org.springframework.stereotype.Component
import org.springframework.web.socket.CloseStatus
import org.springframework.web.socket.TextMessage
import org.springframework.web.socket.WebSocketSession
import org.springframework.web.socket.handler.TextWebSocketHandler
import ru.otus.otuskotlin.MnContext
import ru.otus.otuskotlin.api.v2.apiV2Mapper
import ru.otus.otuskotlin.api.v2.models.IRequest
import ru.otus.otuskotlin.marketplace.biz.MbizDishProcessor
import ru.otus.otuskotlin.models.MnWorkMode
import ru.otus.otuskotlin.springapp.base.SpringWsSessionRepo
import ru.otuskotlin.test.mappers.v2.fromTransport
import ru.otuskotlin.test.mappers.v2.toTransportDish
import ru.otuskotlin.test.mappers.v2.toTransportInit


@Component
class WsDishHandlerV2(
    private val processor: MbizDishProcessor,
    private val sessions: SpringWsSessionRepo,
) : TextWebSocketHandler() {

    override fun afterConnectionEstablished(sess: WebSocketSession) = runBlocking {
        val session = SpringWsSessionV2(sess)
        sessions.add(session)

        val context = MnContext()
        // TODO убрать, когда заработает обычный режим
        context.workMode = MnWorkMode.STUB
        processor.exec(context)
        session.send(context.toTransportInit())
    }

    override fun handleTextMessage(sess: WebSocketSession, message: TextMessage) = runBlocking {
        val session = SpringWsSessionV2(sess)
        val ctx = MnContext(timeStart = Clock.System.now())

        try {
            val request = apiV2Mapper.decodeFromString<IRequest>(message.payload)
            ctx.fromTransport(request)
            processor.exec(ctx)
            session.send(ctx.toTransportDish())
        } catch (e: Exception) {
            ctx.errors.add(e.asMnError())
            session.send(ctx.toTransportInit())
        }
    }

    override fun afterConnectionClosed(session: WebSocketSession, status: CloseStatus) {
        sessions.remove(SpringWsSessionV2(session))
    }
}
