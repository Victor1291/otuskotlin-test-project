package ru.otus.otuskotlin.markeplace.springapp.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import ru.otus.otuskotlin.marketplace.biz.MbizDishProcessor

@Suppress("unused")
@Configuration
class CorConfig {
    @Bean
    fun processor() = MbizDishProcessor()
}
