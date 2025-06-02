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

class BizRepoReadTest {
    private val userId = AdUserId("321")
    private val command = AdCommand.READ
    private val initAd = Ad(
        id = AdId("123"),
        title = "abc",
        authors = "abc",
        publishing = "abc",
        year = 2025,
        adType = AdType.DEMAND,
        price = BigDecimal.parseString("700"),
        ownerId = userId
    )
    private val repo = AdRepositoryMock(
        invokeReadAd = {
            DbAdResponseOk(data = initAd)
        }
    )
    private val settings = AdCorSettings(repoTest = repo)
    private val processor = AdProcessor(settings)

    @Test
    fun repoReadSuccessTest() = runTest {
        val ctx = AdContext(
            command = command,
            state = AdState.NONE,
            workMode = AdWorkMode.TEST,
            adRequest = Ad(
                id = AdId("123")
            )
        )
        processor.exec(ctx)
        assertEquals(AdState.FINISHING, ctx.state)
        assertEquals(initAd.id, ctx.adResponse.id)
        assertEquals(initAd.title, ctx.adResponse.title)
        assertEquals(initAd.authors, ctx.adResponse.authors)
        assertEquals(initAd.publishing, ctx.adResponse.publishing)
        assertEquals(initAd.year, ctx.adResponse.year)
        assertEquals(initAd.price, ctx.adResponse.price)
        assertEquals(AdType.DEMAND, ctx.adResponse.adType)
    }

    @Test
    fun repoReadNotFoundTest() = repoNotFoundTest(command)
}