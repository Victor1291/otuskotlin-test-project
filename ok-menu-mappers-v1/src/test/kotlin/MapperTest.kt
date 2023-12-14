import models.MnDish
import org.junit.Test
import ru.otus.otuskotlin.MnContext
import ru.otus.otuskotlin.api.v1.models.*
import ru.otus.otuskotlin.models.*
import ru.otus.otuskotlin.stubs.MnStubs
import ru.otuskotlin.test.mappers.v1.fromTransport
import ru.otuskotlin.test.mappers.v1.toTransportDish
import kotlin.test.assertEquals

class MapperTest {
    @Test
    fun fromTransport() {
        val req = DishCreateRequest(
            requestId = "1234",
            debug = DishDebug(
                mode = DishRequestDebugMode.STUB,
                stub = DishRequestDebugStubs.SUCCESS,
            ),
            dish = DishCreateObject(
                title = "title",
                description = "desc",
                price = "10$",
                image = "picture",
                visibility = DishVisibility.PUBLIC,
            ),
        )

        val context = MnContext()
        context.fromTransport(req)

        assertEquals(MnStubs.SUCCESS, context.stubCase)
        assertEquals(MnWorkMode.STUB, context.workMode)
        assertEquals("title", context.dishRequest.title)
        assertEquals(MnDishVisibility.VISIBLE_PUBLIC, context.dishRequest.visibility)
    }

    @Test
    fun toTransport() {
        val context = MnContext(
            requestId = MnRequestId("1234"),
            command = MnCommand.CREATE,
            dishResponse = MnDish(
                title = "title",
                description = "desc",
                price = "10$",
                image  = "picture",
                visibility = MnDishVisibility.VISIBLE_PUBLIC,
            ),
            errors = mutableListOf(
                MnError(
                    code = "err",
                    group = "request",
                    field = "title",
                    message = "wrong title",
                )
            ),
            state = MnState.RUNNING,
        )

        val req = context.toTransportDish() as DishCreateResponse

        assertEquals("1234", req.requestId)
        assertEquals("title", req.dish?.title)
        assertEquals("desc", req.dish?.description)
        assertEquals("10$", req.dish?.price)
        assertEquals("picture", req.dish?.image)

        assertEquals(DishVisibility.PUBLIC, req.dish?.visibility)
        assertEquals(1, req.errors?.size)
        assertEquals("err", req.errors?.firstOrNull()?.code)
        assertEquals("request", req.errors?.firstOrNull()?.group)
        assertEquals("title", req.errors?.firstOrNull()?.field)
        assertEquals("wrong title", req.errors?.firstOrNull()?.message)
    }
}