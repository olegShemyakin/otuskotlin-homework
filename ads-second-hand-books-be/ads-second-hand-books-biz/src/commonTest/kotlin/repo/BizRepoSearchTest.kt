package org.akira.otuskotlin.ads.biz.repo

import com.ionspin.kotlin.bignum.decimal.BigDecimal
import kotlinx.coroutines.test.runTest
import org.akira.otuskotlin.ads.biz.AdProcessor
import org.akira.otuskotlin.ads.common.AdContext
import org.akira.otuskotlin.ads.common.AdCorSettings
import org.akira.otuskotlin.ads.common.models.*
import org.akira.otuskotlin.ads.common.repo.DbAdsResponseOk
import org.akira.otuskotlin.ads.repo.tests.AdRepositoryMock
import kotlin.test.Test
import kotlin.test.assertEquals

class BizRepoSearchTest {
    private val userId = AdUserId("321")
    private val command = AdCommand.SEARCH
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
        invokeSearchAd = {
            DbAdsResponseOk(data = listOf(initAd))
        }
    )
    private val settings = AdCorSettings(repoTest = repo)
    private val processor = AdProcessor(settings)

    @Test
    fun repoSearchSuccessTest() = runTest {
        val ctx = AdContext(
            command = command,
            state = AdState.NONE,
            workMode = AdWorkMode.TEST,
            adFilterRequest = AdFilter(
                searchString = "abc",
                adType = AdType.DEMAND
            )
        )
        processor.exec(ctx)
        assertEquals(AdState.FINISHING, ctx.state)
        assertEquals(1, ctx.adsResponse.size)
    }
}