package ru.otus.otuskotlin.marketplace.m1l5.homework.hard

import kotlinx.coroutines.*
import ru.otus.otuskotlin.marketplace.m1l5.homework.hard.dto.Dictionary
import java.io.File
import java.math.BigInteger
import kotlin.test.Test

class HWHard {
    @Test
    fun hardHw() {
        val dictionaryApi = DictionaryApi()
        val words = FileReader.readFile().split(" ", "\n").toSet()

        val dictionaries = findWords(dictionaryApi, words, Locale.EN)

        dictionaries.filterNotNull().map { dictionary ->
            print("For word ${dictionary.word} i found examples: ")
            println(
                dictionary.meanings
                    .mapNotNull { definition ->
                        val r = definition.definitions
                            .mapNotNull { it.example.takeIf { it?.isNotBlank() == true } }
                            .takeIf { it.isNotEmpty() }
                        r
                    }
                    .takeIf { it.isNotEmpty() }
            )
        }
    }

    private fun findWords(
        dictionaryApi: DictionaryApi,
        words: Set<String>,
        @Suppress("SameParameterValue") locale: Locale
    ): List<Dictionary?> {
        // make some suspensions and async
        var value = 0
       return runBlocking(Dispatchers.Default) {
            val result: MutableList<Dictionary?> = mutableListOf()
            val newWords = words.toMutableList()
            launch {
                repeat(newWords.size) {
                    launch {
                        println("Start find '${newWords[it]}' - launch $it  ")
                        value++
                        result.add(dictionaryApi.findWord(locale, newWords[it]))
                        //println("Finish[$it] ")
                    }
                }
            }.join()
            return@runBlocking result
        }

    }


object FileReader {
    fun readFile(): String =
        File(
            this::class.java.classLoader.getResource("words.txt")?.toURI()
                ?: throw RuntimeException("Can't read file")
        ).readText()
}
}
