package org.akira.otuskotlin.ads.biz.repo

import com.ionspin.kotlin.bignum.decimal.BigDecimal
import kotlinx.coroutines.test.runTest
import org.akira.otuskotlin.ads.biz.AdProcessor
import org.akira.otuskotlin.ads.common.AdContext
import org.akira.otuskotlin.ads.common.AdCorSettings
import org.akira.otuskotlin.ads.common.models.*
import org.akira.otuskotlin.ads.common.repo.DbAdResponseErr
import org.akira.otuskotlin.ads.common.repo.DbAdResponseOk
import org.akira.otuskotlin.ads.repo.tests.AdRepositoryMock
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class BizRepoDeleteTest {
    private val userId = AdUserId("321")
    private val command = AdCommand.DELETE
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
            DbAdResponseOk(
                data = initAd
            )
        },
        invokeDeleteAd = {
            if (it.id == initAd.id) DbAdResponseOk(data = initAd)
            else DbAdResponseErr()
        }
    )
    private val settings by lazy {
        AdCorSettings(repoTest = repo)
    }
    private val processor = AdProcessor(settings)

    @Test
    fun repoDeleteSuccessTest() = runTest {
        val adToDelete = Ad(
            id = AdId("123"),
            lock = AdLock("123-234-abc-ABC")
        )
        val ctx = AdContext(
            command = command,
            state = AdState.NONE,
            workMode = AdWorkMode.TEST,
            adRequest = adToDelete
        )
        processor.exec(ctx)
        assertEquals(AdState.FINISHING, ctx.state)
        assertTrue { ctx.errors.isEmpty() }
        assertEquals(initAd.id, ctx.adResponse.id)
    }

    @Test
    fun repoDeleteNotFoundTest() = repoNotFoundTest(command)
}