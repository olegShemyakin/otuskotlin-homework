package org.akira.otuskotlin.ads.biz.repo

import com.ionspin.kotlin.bignum.decimal.BigDecimal
import kotlinx.coroutines.test.runTest
import org.akira.otuskotlin.ads.biz.AdProcessor
import org.akira.otuskotlin.ads.common.AdContext
import org.akira.otuskotlin.ads.common.AdCorSettings
import org.akira.otuskotlin.ads.common.models.*
import org.akira.otuskotlin.ads.common.repo.DbAdResponseOk
import org.akira.otuskotlin.ads.repo.tests.AdRepositoryMock
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

class BizRepoCreateTest {
    private val userId = AdUserId("321")
    private val command = AdCommand.CREATE
    private val uuid = "da8c68ee-b913-4d59-999b-90af9ec3519f"
    private val repo = AdRepositoryMock(
        invokeCreateAd = {
            DbAdResponseOk(
                data = Ad(
                    id = AdId(uuid),
                    title = it.ad.title,
                    authors = it.ad.authors,
                    publishing = it.ad.publishing,
                    year = it.ad.year,
                    adType = it.ad.adType,
                    price = it.ad.price,
                    ownerId = userId
                )
            )
        }
    )
    private val settings = AdCorSettings(
        repoTest = repo
    )
    private val processor = AdProcessor(settings)

    @Test
    fun repoCreateSuccessTest() = runTest {
        val ctx = AdContext(
            command = command,
            state = AdState.NONE,
            workMode = AdWorkMode.TEST,
            adRequest = Ad(
                title = "abc",
                authors = "abc",
                publishing = "abc",
                year = 2025,
                adType = AdType.DEMAND,
                price = BigDecimal.parseString("700.00")
            )
        )
        processor.exec(ctx)
        assertEquals(AdState.FINISHING, ctx.state)
        assertNotEquals(AdId.NONE, ctx.adResponse.id)
        assertEquals("abc", ctx.adResponse.title)
        assertEquals("abc", ctx.adResponse.authors)
        assertEquals("abc", ctx.adResponse.publishing)
        assertEquals(2025, ctx.adResponse.year)
        assertEquals(BigDecimal.parseString("700.00"), ctx.adResponse.price)
        assertEquals(AdType.DEMAND, ctx.adResponse.adType)
    }
}