package homework.easy

import kotlinx.coroutines.yield

fun generateNumbers() = (0..10000).map {
    (0..100).random()
}
