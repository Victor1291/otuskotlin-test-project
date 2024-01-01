package ru.otus.otuskotlin.ws

interface IMnWsSessionRepo {

    fun add(session: IMnWsSession)
    fun clearAll()
    fun remove(session: IMnWsSession)
    suspend fun <K> sendAll(obj: K)
}