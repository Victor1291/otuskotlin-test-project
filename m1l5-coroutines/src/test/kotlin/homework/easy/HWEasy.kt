package homework.easy

import kotlinx.coroutines.*
import kotlin.test.Test

class HWEasy {

    @Test
    fun easyHw() {
        runBlocking {

            val numbers = generateNumbers()
            val toFind = 10
            val toFindOther = 1000

            val foundNumbers = listOf(
                async { findNumberInList(toFind, numbers) },
                async { findNumberInList(toFindOther, numbers) }
            )

            foundNumbers.forEach {
                if (it.await() != -1) {
                    println("Your number $it found!")
                } else {
                    println("Not found number $toFind || $toFindOther")
                }
            }
        }
    }
}
