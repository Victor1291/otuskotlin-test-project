package homework.easy

import kotlinx.coroutines.delay
import kotlinx.coroutines.yield

suspend fun findNumberInList(toFind: Int, numbers: List<Int>): Int {
    delay(2000L)
    return numbers.firstOrNull { it == toFind } ?: -1
}
