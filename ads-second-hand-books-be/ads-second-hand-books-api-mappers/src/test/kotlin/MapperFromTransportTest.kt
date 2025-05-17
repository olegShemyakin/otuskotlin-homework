import com.ionspin.kotlin.bignum.decimal.BigDecimal
import org.akira.otuskotlin.ads.api.models.*
import org.akira.otuskotlin.ads.common.AdContext
import org.akira.otuskotlin.ads.common.models.AdCommand
import org.akira.otuskotlin.ads.common.models.AdType
import org.akira.otuskotlin.ads.common.models.AdWorkMode
import org.akira.otuskotlin.ads.common.stubs.AdsStubs
import org.akira.otuskotlin.ads.mappers.fromTransport
import kotlin.test.Test
import kotlin.test.assertEquals

class MapperFromTransportTest {

    @Test
    fun fromTransport_Create() {
        val req = AdCreateRequest(
            debug = AdDebug(
                mode = AdRequestDebugMode.STUB,
                stub = AdRequestDebugStubs.SUCCESS
            ),
            ad = AdCreateObject(
                title = TITLE,
                authors = AUTHORS,
                publishing = PUBLISHING,
                year = YEAR,
                typeAd = TYPE_AD,
                price = PRICE
            )
        )

        val ctx = AdContext()
        ctx.fromTransport(req)

        assertEquals(AdCommand.CREATE, ctx.command)
        assertEquals(AdsStubs.SUCCESS, ctx.stubCase)
        assertEquals(AdWorkMode.STUB, ctx.workMode)
        assertEquals(TITLE, ctx.adRequest.title)
        assertEquals(AUTHORS, ctx.adRequest.authors)
        assertEquals(PUBLISHING, ctx.adRequest.publishing)
        assertEquals(YEAR, ctx.adRequest.year)
        assertEquals(AdType.DEMAND, ctx.adRequest.adType)
        assertEquals(PRICE_BIG_DECIMAL, ctx.adRequest.price)
    }

    @Test
    fun fromTransport_Read() {
        val req = AdReadRequest(
            debug = AdDebug(
                mode = AdRequestDebugMode.STUB,
                stub = AdRequestDebugStubs.SUCCESS
            ),
            ad = AdReadObject(id = ID)
        )

        val ctx = AdContext()
        ctx.fromTransport(req)

        assertEquals(AdCommand.READ, ctx.command)
        assertEquals(AdsStubs.SUCCESS, ctx.stubCase)
        assertEquals(AdWorkMode.STUB, ctx.workMode)
        assertEquals(ID, ctx.adRequest.id.asString())
    }

    @Test
    fun fromTransport_Update() {
        val req = AdUpdateRequest(
            debug = AdDebug(
                mode = AdRequestDebugMode.STUB,
                stub = AdRequestDebugStubs.SUCCESS
            ),
            ad = AdUpdateObject(
                id = ID,
                year = YEAR
            )
        )

        val ctx = AdContext()
        ctx.fromTransport(req)

        assertEquals(AdCommand.UPDATE, ctx.command)
        assertEquals(AdsStubs.SUCCESS, ctx.stubCase)
        assertEquals(AdWorkMode.STUB, ctx.workMode)
        assertEquals(ID, ctx.adRequest.id.asString())
        assertEquals(YEAR, ctx.adRequest.year)
    }

    @Test
    fun fromTransport_Delete() {
        val req = AdDeleteRequest(
            debug = AdDebug(
                mode = AdRequestDebugMode.STUB,
                stub = AdRequestDebugStubs.SUCCESS
            ),
            ad = AdDeleteObject(id = ID)
        )

        val ctx = AdContext()
        ctx.fromTransport(req)

        assertEquals(AdCommand.DELETE, ctx.command)
        assertEquals(AdsStubs.SUCCESS, ctx.stubCase)
        assertEquals(AdWorkMode.STUB, ctx.workMode)
        assertEquals(ID, ctx.adRequest.id.asString())
    }

    @Test
    fun fromTransport_Search() {
        val req = AdSearchRequest(
            debug = AdDebug(
                mode = AdRequestDebugMode.STUB,
                stub = AdRequestDebugStubs.SUCCESS
            ),
            adFilter = AdSearchFilter(searchString = SEARCH)
        )

        val ctx = AdContext()
        ctx.fromTransport(req)

        assertEquals(AdCommand.SEARCH, ctx.command)
        assertEquals(AdsStubs.SUCCESS, ctx.stubCase)
        assertEquals(AdWorkMode.STUB, ctx.workMode)
        assertEquals(SEARCH, ctx.adFilterRequest.searchString)
    }

    companion object {
        private const val TITLE = "Book"
        private const val AUTHORS = "John Smith"
        private const val PUBLISHING = "AZBOOKA"
        private const val YEAR = 2025
        private val TYPE_AD = TypeAd.DEMAND
        private const val PRICE = "500.99"
        private val PRICE_BIG_DECIMAL = BigDecimal.parseString(PRICE)
        private const val ID = "64acaad0-fdbb-40fa-a2ec-e157bfa5ae87"
        private const val SEARCH = "poisk"
    }
}