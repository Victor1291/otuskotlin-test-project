package flow

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.*
import kotlin.test.Test

class Ex1FlowOperatorsTest {

    /**
     * Простейшая цепочка flow
     */
    @Test
    fun simple(): Unit = runBlocking {
        flowOf(1, 2, 3, 4) // билдер
            .onEach { println(it) } // операции ...
            .map { it + 1 }
            .filter { it % 2 == 0 }
            .collect { println("Result number $it") } // терминальный оператор
    }

    /**
     * Хелпер-функция для печати текущего потока
     */
    fun <T> Flow<T>.printThreadName(msg: String) =
        this.onEach { println("Msg = $msg, thread name = ${Thread.currentThread().name}") }

    /**
     * Демонстрация переключения корутин-контекста во flow
     */
    @Test
    @OptIn(DelicateCoroutinesApi::class)
    fun coroutineContextChange(): Unit = runBlocking {
        // Просто создали диспетчера и безопасно положили его в apiDispatcher
        newSingleThreadContext("Api-Thread").use { apiDispatcher ->
            // еще один...
            newSingleThreadContext("Db-Thread").use { dbDispatcher ->

                // Контекст переключается в ОБРАТНОМ ПОРЯДКЕ, т.е. СНИЗУ ВВЕРХ
                flowOf(10, 20, 30) // apiDispatcher
                    .printThreadName("start call") // apiDispatcher
                    .filter { it % 2 == 0 } // apiDispatcher
                    .map {
                        delay(2000)
                        it
                    } // apiDispatcher
                    .printThreadName("api call") // apiDispatcher
                    .flowOn(apiDispatcher) // Переключаем контекст выполнения на apiDispatcher
                    .map { it + 1 } // dbDispatcher
                    .printThreadName("db call") // dbDispatcher
                    .flowOn(dbDispatcher) // Переключаем контекст выполнения на dbDispatcher
                    .printThreadName("last operation") // Default
                    .onEach { println("On each $it") } // Default
                    .collect() // запустится в контексте по умолчанию, т.е. в Dispatchers.Default
            }
        }
    }

    /**
     * Демонстрация тригеров onStart, onCompletion, catch, onEach
     */
    @Test
    fun startersStopers(): Unit = runBlocking {
        flow {
            while (true) {
                val listTurn = List(90) { it + 1 }.shuffled()
                listTurn.forEach {
                    emit(it)
                }
                emit(1)
                delay(1000)
                emit(2)
                delay(1000)
                emit(3)
                delay(1000)
                throw RuntimeException("Custom error!")
            }
        }
            .onStart { println("On start, On each: ") } // Запустится один раз только вначале
            .onCompletion { println(" On completion") } // Запустится один раз только вконце
            .catch { println("Catch: ${it.message}") } // Запустится только при генерации исключения
            .onEach { print(" $it ") } // Генерируется для каждого сообщения
            .collect { }
    }


    /**
     * Демонстрация буферизации.
     * Посмотрите как меняется порядок следования сообщений при применении буфера.
     * Буфер можно выставить в 0, либо поставить любое положительное значение.
     * Попробуйте поменять тип буфера и посмотрите как изменится поведение. Лучше менять при размере буфера 3.
     * Имейте в виду, что инициализация генерации и обработки элемента в цепочке всегда происходит в терминальном
     * операторе.
     */
    @Test
    fun buffering(): Unit = runBlocking {
        val timeInit = System.currentTimeMillis()
        var sleepIndex = 1 // Счетчик инкрементится в терминальном операторе после большой задержки
        var el = 1 // Простой номер сообщения
        flow {
            while (sleepIndex < 5) {
                delay(500)
                print("emitting $sleepIndex - ${System.currentTimeMillis() - timeInit}ms ")
                emit(el++ to sleepIndex)
            }
        }
            .onEach { print("Send to flow: $it - ${System.currentTimeMillis() - timeInit}ms ") }
            //          .buffer(3, BufferOverflow.DROP_LATEST) // Здесь включаем буфер размером 3 элемента
        //    .buffer(3, BufferOverflow.DROP_OLDEST) // Попробуйте разные варианты типов и размеров буферов
           .buffer(3, BufferOverflow.SUSPEND)
            .onEach { print("Processing : $it - ${System.currentTimeMillis() - timeInit}ms ") }
            .collect {
                print("Sleep ${System.currentTimeMillis() - timeInit}ms ")
                println()
                sleepIndex++
                delay(2_000)
            }


    }
/*
Стратегия обработки переполнения буфера в каналах и потоках, которая контролирует, чем придется пожертвовать при переполнении буфера:
SUSPEND — восходящий поток, который отправляет или выдает значение, приостанавливается, пока буфер заполнен.
DROP_OLDEST — удалить самое старое значение в буфере при переполнении, добавить новое значение в буфер, не приостанавливать.
DROP_LATEST — удалить последнее значение, которое добавляется в буфер прямо сейчас при переполнении буфера (чтобы содержимое буфера оставалось прежним), не приостанавливать.

buffer(3, BufferOverflow.DROP_LATEST) // Здесь включаем буфер размером 3 элемента

emitting 1 - 521ms Send to flow: (1, 1) - 531ms Processing : (1, 1) - 537ms Sleep 537ms
emitting 2 - 1037ms Send to flow: (2, 2) - 1037ms emitting 2 - 1537ms Send to flow: (3, 2) - 1537ms emitting 2 - 2038ms Send to flow: (4, 2) - 2038ms emitting 2 - 2538ms Send to flow: (5, 2) - 2538ms Processing : (2, 2) - 2540ms Sleep 2540ms
emitting 3 - 3039ms Send to flow: (6, 3) - 3039ms emitting 3 - 3539ms Send to flow: (7, 3) - 3539ms emitting 3 - 4040ms Send to flow: (8, 3) - 4040ms emitting 3 - 4540ms Send to flow: (9, 3) - 4540ms Processing : (3, 2) - 4541ms Sleep 4541ms
emitting 4 - 5040ms Send to flow: (10, 4) - 5040ms emitting 4 - 5541ms Send to flow: (11, 4) - 5541ms emitting 4 - 6041ms Send to flow: (12, 4) - 6042ms Processing : (4, 2) - 6541ms Sleep 6541ms
emitting 5 - 6542ms Send to flow: (13, 5) - 6542ms Processing : (6, 3) - 8542ms Sleep 8542ms
Processing : (10, 4) - 10542ms Sleep 10542ms
Processing : (13, 5) - 12543ms Sleep 12543ms

 .buffer(3, BufferOverflow.DROP_OLDEST) // Попробуйте разные варианты типов и размеров буферов

emitting 1 - 523ms Send to flow: (1, 1) - 535ms Processing : (1, 1) - 540ms Sleep 540ms
emitting 2 - 1040ms Send to flow: (2, 2) - 1040ms emitting 2 - 1541ms Send to flow: (3, 2) - 1541ms emitting 2 - 2041ms Send to flow: (4, 2) - 2041ms emitting 2 - 2542ms Send to flow: (5, 2) - 2542ms Processing : (3, 2) - 2543ms Sleep 2543ms
emitting 3 - 3042ms Send to flow: (6, 3) - 3042ms emitting 3 - 3543ms Send to flow: (7, 3) - 3543ms emitting 3 - 4043ms Send to flow: (8, 3) - 4043ms Processing : (6, 3) - 4544ms Sleep 4544ms
emitting 4 - 4544ms Send to flow: (9, 4) - 4544ms emitting 4 - 5044ms Send to flow: (10, 4) - 5044ms emitting 4 - 5545ms Send to flow: (11, 4) - 5545ms emitting 4 - 6045ms Send to flow: (12, 4) - 6045ms Processing : (10, 4) - 6544ms Sleep 6544ms
emitting 5 - 6546ms Send to flow: (13, 5) - 6546ms Processing : (11, 4) - 8545ms Sleep 8545ms
Processing : (12, 4) - 10545ms Sleep 10545ms
Processing : (13, 5) - 12546ms Sleep 12546ms

 .buffer(3, BufferOverflow.SUSPEND)

emitting 1 - 521ms Send to flow: (1, 1) - 537ms Processing : (1, 1) - 545ms Sleep 545ms
emitting 2 - 1045ms Send to flow: (2, 2) - 1045ms emitting 2 - 1545ms Send to flow: (3, 2) - 1545ms emitting 2 - 2046ms Send to flow: (4, 2) - 2046ms emitting 2 - 2546ms Send to flow: (5, 2) - 2546ms Processing : (2, 2) - 2548ms Sleep 2548ms
emitting 3 - 3049ms Send to flow: (6, 3) - 3049ms Processing : (3, 2) - 4549ms Sleep 4549ms
emitting 4 - 5049ms Send to flow: (7, 4) - 5050ms Processing : (4, 2) - 6549ms Sleep 6549ms
Processing : (5, 2) - 8550ms Sleep 8550ms
Processing : (6, 3) - 10551ms Sleep 10551ms
Processing : (7, 4) - 12551ms Sleep 12551ms

 */
    /**
     * Демонстрация реализации кастомного оператора для цепочки.
     */
    @Test
    fun customOperator(): Unit = runBlocking {
        fun <T> Flow<T>.zipWithNext(): Flow<Pair<T, T>> = flow {
            var prev: T? = null
            collect { el ->
                prev?.also { pr -> emit(pr to el) } // Здесь корректная проверка на NULL при использовании var
                prev = el
            }
        }

        flowOf(1, 2, 3, 4)
            .zipWithNext()
            .collect { println(it) }
    }

    /**
     * Терминальный оператор toList.
     * Попробуйте другие: collect, toSet, first, single (потребуется изменить билдер)
     */
    @Test
    fun toListTermination(): Unit = runBlocking {
        val list = flow {
            emit(1)
            delay(100)
            emit(2)
            delay(100)
        }
            .onEach { println("$it") }
            .toList()

        println("List: $list")
    }

    /**
     * Работа с бесконечными билдерами flow
     */
    @Test
    fun infiniteBuilder(): Unit = runBlocking {
        val list = flow {
            var index = 0
            // здесь бесконечный цикл, не переполнения не будет из-за take
            while (true) {
                emit(index++)
                delay(100)
            }
        }
            .onEach { println("$it") }
            .take(10) // Попробуйте поменять аргумент и понаблюдайте за размером результирующего списка
            .toList()

        println("List: $list")
    }

    /**
     * Демонстрация sample и debounce.
     * Попробуйте различные аргументы этих функций.
     */
    @OptIn(FlowPreview::class)
    @Test
    fun sampleDebounce() = runBlocking {
        val f = flow {
            repeat(20) {
                delay(100)
                emit(it)
                delay(400) // Посмотрите как поменяется поведение при отключении эти двух строк.
                emit("${it}a")
            }
        }

        println("SAMPLE")
        f.sample(200).collect {
            print(" $it")
        }
        println()
        println("DEBOUNCE")
        f.debounce(200).collect {
            print(" $it")
        }
        println()
    }

}


