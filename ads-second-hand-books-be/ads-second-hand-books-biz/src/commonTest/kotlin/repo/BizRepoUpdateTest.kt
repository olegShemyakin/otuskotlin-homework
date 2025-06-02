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

class BizRepoUpdateTest {
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
        ownerId = userId,
        lock = AdLock("123-234-abc-ABC")
    )
    private val repo = AdRepositoryMock(
        invokeReadAd = {
            DbAdResponseOk(data = initAd)
        },
        invokeUpdateAd = {
            DbAdResponseOk(
                data = Ad(
                    id = AdId("123"),
                    title = "xyz",
                    authors = "xyz",
                    publishing = "xyz",
                    year = 2025,
                    adType = AdType.DEMAND,
                    price = BigDecimal.parseString("700")
                )
            )
        }
    )
    private val settings = AdCorSettings(repoTest = repo)
    private val processor = AdProcessor(settings)

    @Test
    fun repoUpdateSuccessTest() = runTest {
        val adToUpdate = Ad(
            id = AdId("123"),
            title = "xyz",
            authors = "xyz",
            publishing = "xyz",
            year = 2025,
            adType = AdType.DEMAND,
            price = BigDecimal.parseString("700"),
            lock = AdLock("123-234-abc-ABC"),
        )
        val ctx = AdContext(
            command = command,
            state = AdState.NONE,
            workMode = AdWorkMode.TEST,
            adRequest = adToUpdate,
        )
        processor.exec(ctx)
        assertEquals(AdState.FINISHING, ctx.state)
        assertEquals(adToUpdate.id, ctx.adResponse.id)
        assertEquals(adToUpdate.title, ctx.adResponse.title)
        assertEquals(adToUpdate.authors, ctx.adResponse.authors)
        assertEquals(adToUpdate.publishing, ctx.adResponse.publishing)
        assertEquals(adToUpdate.year, ctx.adResponse.year)
        assertEquals(adToUpdate.adType, ctx.adResponse.adType)
        assertEquals(adToUpdate.price, ctx.adResponse.price)
    }

    @Test
    fun repoUpdateNotFountTest() = repoNotFoundTest(command)
}