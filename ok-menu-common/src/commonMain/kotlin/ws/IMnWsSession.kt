package ru.otus.otuskotlin.ws

interface IMnWsSession {

    suspend fun <T> send(obj: T)
    companion object {
        val NONE = object : IMnWsSession {
            override suspend fun <T> send(obj: T) {

            }
        }
    }
}