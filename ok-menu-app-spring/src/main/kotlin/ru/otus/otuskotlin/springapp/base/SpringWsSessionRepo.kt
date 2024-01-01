package ru.otus.otuskotlin.springapp.base

import ru.otus.otuskotlin.ws.IMnWsSession
import ru.otus.otuskotlin.ws.IMnWsSessionRepo

class SpringWsSessionRepo : IMnWsSessionRepo {
    private val sessions: MutableSet<IMnWsSession> = mutableSetOf()
    override fun add(session: IMnWsSession) {
        sessions.add(session)
    }

    override fun clearAll() {
        sessions.clear()
    }

    override fun remove(session: IMnWsSession) {
        sessions.remove(session)
    }

    override suspend fun <T> sendAll(obj: T) {
        sessions.forEach { it.send(obj) }
    }
}