package ru.otus.otuskotlin.springapp.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import ru.otus.otuskotlin.marketplace.biz.MbizDishProcessor
import ru.otus.otuskotlin.springapp.base.SpringWsSessionRepo

@Suppress("unused")
@Configuration
class CorConfig {
    @Bean
    fun processor() = MbizDishProcessor()

    @Bean
    fun wsRepo(): SpringWsSessionRepo = SpringWsSessionRepo()
}
