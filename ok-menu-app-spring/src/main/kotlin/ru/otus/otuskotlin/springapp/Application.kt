package ru.otus.otuskotlin.springapp

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class Application


// swagger URL: http://localhost:8080/swagger-ui.html

fun main(args: Array<String>) {
    runApplication<ru.otus.otuskotlin.springapp.Application>(*args)
}
