package ru.otus.otuskotlin.marketplace.m1l5.homework.hard

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import kotlinx.coroutines.yield
import ru.otus.otuskotlin.marketplace.m1l5.homework.hard.dto.Dictionary
import okhttp3.Response

class DictionaryApi(
    private val objectMapper: ObjectMapper = jacksonObjectMapper()
) {

   suspend fun findWord(locale: Locale, word: String): Dictionary? { // make something with context
        val url = "$DICTIONARY_API/${locale.code}/$word"
        println("Searching $url")
       yield()
        return getBody(HttpClient.get(url).execute())?.firstOrNull()
    }


    private fun getBody(response: Response): List<Dictionary>? {
        if (!response.isSuccessful) {
            return emptyList()
        }

        return response.body?.let {
            objectMapper.readValue(it.string())
        }
    }
}
