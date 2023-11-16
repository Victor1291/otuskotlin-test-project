package ru.otus.otuskotlin.marketplace.m1l5.homework.hard

import kotlinx.coroutines.*
import ru.otus.otuskotlin.marketplace.m1l5.homework.hard.dto.Dictionary
import java.io.File
import kotlin.test.Test

class HWHard {
    @Test
    fun hardHw() {
        val dictionaryApi = DictionaryApi()
        val words = FileReader.readFile().split(" ", "\n").toSet()

        //val dictionaries = findWords(dictionaryApi, words, Locale.EN)
        //val dictionaries = findWords2(dictionaryApi, words, Locale.EN)
        val dictionaries = findWords3(dictionaryApi, words, Locale.EN)
      //  val dictionaries = findWords4(dictionaryApi, words, Locale.EN) не дожидается результата в СoroutineScope , применить флоу

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


    //51 sec
    private fun findWords2(
        dictionaryApi: DictionaryApi,
        words: Set<String>,
        @Suppress("SameParameterValue") locale: Locale
    ): List<Dictionary?> {
        // make some suspensions and async

        return runBlocking(Dispatchers.Default) {
            val result: MutableList<Dictionary?> = mutableListOf()
            var list: List<Dictionary?> = mutableListOf()
            val newWords = words.toMutableList()
            repeat(newWords.size) {
                println("Start find '${newWords[it]}' - launch $it  ")
                val new = async { dictionaryApi.findWord(locale, newWords[it]) }.await()
                result.add(new)
                //println("Finish[$it] ")
            }
            return@runBlocking result.toList()
        }

    }


    //21 sec with async with Dispatcher Default
    // 5 sec whith Dispatcher IO
    private fun findWords3(
        dictionaryApi: DictionaryApi,
        words: Set<String>,
        @Suppress("SameParameterValue") locale: Locale
    ): List<Dictionary?> {
        // make some suspensions and async

        return runBlocking(Dispatchers.IO) {
            val result: MutableList<Deferred<Dictionary?>> = mutableListOf()
            var list: List<Dictionary?> = mutableListOf()
            val newWords = words.toMutableList()
            repeat(newWords.size) {
                println("Start find '${newWords[it]}' - launch $it  ")
                val new = async { dictionaryApi.findWord(locale, newWords[it]) }
                result.add(new)
                //println("Finish[$it] ")
            }
            list = result.map {
                it.await()
            }
            return@runBlocking list.toList()
        }
    }

    private fun findWords4(
        dictionaryApi: DictionaryApi,
        words: Set<String>,
        @Suppress("SameParameterValue") locale: Locale
    ): List<Dictionary?> {
        // make some suspensions and async
        val scope = CoroutineScope(Job() + Dispatchers.IO)
        var list: List<Dictionary?> = mutableListOf()
      val job = scope.launch {
            val result: MutableList<Deferred<Dictionary?>> = mutableListOf()

            val newWords = words.toMutableList()
            repeat(newWords.size) {
                println("Start find '${newWords[it]}' - launch $it  ")
                val new = async { dictionaryApi.findWord(locale, newWords[it]) }
                result.add(new)
                //println("Finish[$it] ")
            }
            list = result.map {
                it.await()
            }
           joinAll()
            cancel()
        }
        return  list.toList()
    }

    object FileReader {
        fun readFile(): String =
            File(
                this::class.java.classLoader.getResource("words.txt")?.toURI()
                    ?: throw RuntimeException("Can't read file")
            ).readText()
    }
}
