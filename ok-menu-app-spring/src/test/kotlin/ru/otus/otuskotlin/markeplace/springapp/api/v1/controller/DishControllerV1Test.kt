package ru.otus.otuskotlin.markeplace.springapp.api.v1.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.ninjasquad.springmockk.MockkBean
import io.mockk.coVerify
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.http.MediaType
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.web.reactive.function.BodyInserters
import ru.otus.otuskotlin.MnContext
import ru.otus.otuskotlin.api.v1.models.*
import ru.otus.otuskotlin.marketplace.biz.MbizDishProcessor
import ru.otuskotlin.test.mappers.v1.*

@WebFluxTest(DishControllerV1::class)
internal class DishControllerV1Test {
    @Autowired
    private lateinit var webClient: WebTestClient

    @MockkBean(relaxUnitFun = true)
    private lateinit var processor: MbizDishProcessor

    @Autowired
    private lateinit var mapper: ObjectMapper

    @Test
    fun createDish() = testStubDish(
        "/v1/dish/create",
        DishCreateRequest(),
        MnContext().toTransportCreate()
    )

    @Test
    fun readAd() = testStubDish(
        "/v1/dish/read",
        DishReadRequest(),
        MnContext().toTransportRead()
    )

    @Test
    fun updateAd() = testStubDish(
        "/v1/dish/update",
        DishUpdateRequest(),
        MnContext().toTransportUpdate()
    )

    @Test
    fun deleteAd() = testStubDish(
        "/v1/dish/delete",
        DishDeleteRequest(),
        MnContext().toTransportDelete()
    )

    @Test
    fun searchAd() = testStubDish(
        "/v1/dish/search",
        DishSearchRequest(),
        MnContext().toTransportSearch()
    )

    private inline fun <reified Req : Any, reified Res : Any> testStubDish(
        url: String,
        requestObj: Req,
        responseObj: Res,
    ) {
        webClient
            .post()
            .uri(url)
            .contentType(MediaType.APPLICATION_JSON)
            .body(BodyInserters.fromValue(requestObj))
            .exchange()
            .expectStatus().isOk
            .expectBody(Res::class.java)
            .value {
                println("RESPONSE: $it")
                val expectedResponse =  mapper.readValue(mapper.writeValueAsString(responseObj as IResponse), IResponse::class.java)
                Assertions.assertThat(it).isEqualTo(expectedResponse)
            }
        coVerify { processor.exec(any()) }
    }
}
