package ru.otus.otuskotlin.marketplace.m1l5.homework.hard

import kotlinx.coroutines.*
import okhttp3.internal.wait
import ru.otus.otuskotlin.marketplace.m1l5.homework.hard.dto.Dictionary
import java.io.File
import kotlin.test.Test

class HWHard {
    @Test
    fun hardHw()  {

        val dictionaryApi = DictionaryApi()
        val words = FileReader.readFile().split(" ", "\n").toSet()

       // val dictionaries = findWords(dictionaryApi, words, Locale.EN)
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

// 8sec
    private fun findWords(
        dictionaryApi: DictionaryApi,
        words: Set<String>,
        @Suppress("SameParameterValue") locale: Locale
    ) = runBlocking {
        // make some suspensions and async
        words.map { word ->
            async (context = Dispatchers.IO){
                dictionaryApi.findWord(locale, word)
            }
        }.awaitAll()
    }

    /*
Awaits for completion of given deferred values without blocking a thread and resumes normally with the list of
 values when all deferred computations are complete or resumes with the first thrown exception if any of
 computations complete exceptionally including cancellation.

This function is not equivalent to deferreds.map { it.await() } which fails only when it sequentially gets
to wait for the failing deferred, while this awaitAll fails immediately as soon as any of the deferreds fail.

This suspending function is cancellable. If the Job of the current coroutine is cancelled or completed while
this suspending function is waiting, this function immediately resumes with CancellationException.
There is a prompt cancellation guarantee. If the job was cancelled while this function was suspended,
it will not resume successfully. See suspendCancellableCoroutine documentation for low-level details.
 */


    /*
    Awaits for completion of given deferred values without blocking a thread and resumes normally with the
    list of values when all deferred computations are complete or resumes with the first thrown exception
    if any of computations complete exceptionally including cancellation.

    This function is not equivalent to this.map { it.await() } which fails only when it sequentially gets
    to wait for the failing deferred, while this awaitAll fails immediately as soon as any of the deferreds fail.

    This suspending function is cancellable. If the Job of the current coroutine is cancelled or completed
    while this suspending function is waiting, this function immediately resumes with CancellationException.
    There is a prompt cancellation guarantee. If the job was cancelled while this function was suspended,
    it will not resume successfully. See suspendCancellableCoroutine documentation for low-level details.

 */
    private fun findWordsTwo(
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
        return list.toList()
    }

}

object FileReader {
    fun readFile(): String =
        File(
            this::class.java.classLoader.getResource("words.txt")?.toURI()
                ?: throw RuntimeException("Can't read file")
        ).readText()
}

