import com.ionspin.kotlin.bignum.decimal.BigDecimal
import org.akira.otuskotlin.ads.api.models.*
import org.akira.otuskotlin.ads.common.AdContext
import org.akira.otuskotlin.ads.common.models.*
import org.akira.otuskotlin.ads.mappers.toTransport
import kotlin.test.Test
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals
import kotlin.test.assertNull

class MapperToTransportTest {

    @Test
    fun toTransport_Create() {
        val ctx = AdContext(
            requestId = AdRequestId(ID),
            command = AdCommand.CREATE,
            adResponse = Ad(
                title = TITLE,
                authors = AUTHORS,
                publishing = PUBLISHING,
                year = YEAR,
                adType = AD_TYPE,
                price = PRICE_BIG_DECIMAL
            ),
            errors = mutableListOf(
                AdError(
                    code = "err",
                    group = "request",
                    field = "title",
                    message = "wrong title"
                )
            ),
            state = AdState.RUNNING
        )

        val req = ctx.toTransport() as AdCreateResponse

        assertEquals(RESPONSE_RESULT, req.result)
        assertContentEquals(ERRORS, req.errors)
        assertEquals(TITLE, req.ad?.title)
        assertEquals(AUTHORS, req.ad?.authors)
        assertEquals(PUBLISHING, req.ad?.publishing)
        assertEquals(YEAR, req.ad?.year)
        assertEquals(TYPE_AD, req.ad?.typeAd)
        assertEquals(PRICE, req.ad?.price)
    }

    @Test
    fun toTransport_Read() {
        val ctx = AdContext(
            requestId = AdRequestId(ID),
            command = AdCommand.READ,
            adResponse = Ad(
                title = TITLE,
                authors = AUTHORS,
                publishing = PUBLISHING,
                year = YEAR,
                adType = AD_TYPE,
                price = PRICE_BIG_DECIMAL
            ),
            state = AdState.RUNNING
        )

        val req = ctx.toTransport() as AdReadResponse

        assertEquals(RESPONSE_RESULT, req.result)
        assertNull(req.errors)
        assertEquals(TITLE, req.ad?.title)
        assertEquals(AUTHORS, req.ad?.authors)
        assertEquals(PUBLISHING, req.ad?.publishing)
        assertEquals(YEAR, req.ad?.year)
        assertEquals(TYPE_AD, req.ad?.typeAd)
        assertEquals(PRICE, req.ad?.price)
    }

    @Test
    fun toTransport_Update() {
        val ctx = AdContext(
            requestId = AdRequestId(ID),
            command = AdCommand.UPDATE,
            adResponse = Ad(
                title = TITLE,
                authors = AUTHORS,
                publishing = PUBLISHING,
                year = YEAR,
                adType = AD_TYPE,
                price = PRICE_BIG_DECIMAL
            ),
            state = AdState.RUNNING
        )

        val req = ctx.toTransport() as AdUpdateResponse

        assertEquals(RESPONSE_RESULT, req.result)
        assertNull(req.errors)
        assertEquals(TITLE, req.ad?.title)
        assertEquals(AUTHORS, req.ad?.authors)
        assertEquals(PUBLISHING, req.ad?.publishing)
        assertEquals(YEAR, req.ad?.year)
        assertEquals(TYPE_AD, req.ad?.typeAd)
        assertEquals(PRICE, req.ad?.price)
    }

    @Test
    fun toTransport_Delete() {
        val ctx = AdContext(
            requestId = AdRequestId(ID),
            command = AdCommand.DELETE,
            adResponse = Ad(
                title = TITLE,
                authors = AUTHORS,
                publishing = PUBLISHING,
                year = YEAR,
                adType = AD_TYPE,
                price = PRICE_BIG_DECIMAL
            ),
            state = AdState.RUNNING
        )

        val req = ctx.toTransport() as AdDeleteResponse

        assertEquals(RESPONSE_RESULT, req.result)
        assertNull(req.errors)
        assertEquals(TITLE, req.ad?.title)
        assertEquals(AUTHORS, req.ad?.authors)
        assertEquals(PUBLISHING, req.ad?.publishing)
        assertEquals(YEAR, req.ad?.year)
        assertEquals(TYPE_AD, req.ad?.typeAd)
        assertEquals(PRICE, req.ad?.price)
    }

    @Test
    fun toTransport_Search() {
        val ctx = AdContext(
            requestId = AdRequestId(ID),
            command = AdCommand.SEARCH,
            adsResponse = mutableListOf(
                Ad(
                    title = TITLE,
                    authors = AUTHORS,
                    publishing = PUBLISHING,
                    year = YEAR,
                    adType = AD_TYPE,
                    price = PRICE_BIG_DECIMAL
                )
            ),
            state = AdState.RUNNING
        )

        val req = ctx.toTransport() as AdSearchResponse

        assertEquals(RESPONSE_RESULT, req.result)
        assertNull(req.errors)
        assertContentEquals(ADS_RESPONSE, req.ads)
    }

    companion object {
        private const val TITLE = "Book"
        private const val AUTHORS = "John Smith"
        private const val PUBLISHING = "AZBOOKA"
        private const val YEAR = 2025
        private val TYPE_AD = TypeAd.DEMAND
        private val AD_TYPE = AdType.DEMAND
        private const val PRICE = "500.99"
        private val PRICE_BIG_DECIMAL = BigDecimal.parseString(PRICE)
        private const val ID = "64acaad0-fdbb-40fa-a2ec-e157bfa5ae87"
        private val RESPONSE_RESULT = ResponseResult.SUCCESS
        private val ERRORS = listOf(
            Error(
                code = "err",
                group = "request",
                field = "title",
                message = "wrong title"
            )
        )
        private val ADS_RESPONSE = listOf(
            AdResponseObject(
                title = TITLE,
                authors = AUTHORS,
                publishing = PUBLISHING,
                year = YEAR,
                typeAd = TYPE_AD,
                price = PRICE
            )
        )
    }
}