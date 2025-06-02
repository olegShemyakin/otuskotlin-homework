package org.akira.otuskotlin.ads.biz.repo

import com.ionspin.kotlin.bignum.decimal.BigDecimal
import kotlinx.coroutines.test.runTest
import org.akira.otuskotlin.ads.biz.AdProcessor
import org.akira.otuskotlin.ads.common.AdContext
import org.akira.otuskotlin.ads.common.AdCorSettings
import org.akira.otuskotlin.ads.common.models.*
import org.akira.otuskotlin.ads.common.repo.DbAdResponseOk
import org.akira.otuskotlin.ads.common.repo.errorNotFound
import org.akira.otuskotlin.ads.repo.tests.AdRepositoryMock
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

private val initAd = Ad(
    id = AdId("123"),
    title = "test",
    authors = "test",
    publishing = "test",
    year = 2025,
    adType = AdType.DEMAND,
    price = BigDecimal.parseString("700.00")
)

private val repo = AdRepositoryMock(
    invokeReadAd = {
        if (it.id == initAd.id) {
            DbAdResponseOk(
                data = initAd
            )
        } else errorNotFound(it.id)
    }
)

private val settings = AdCorSettings(repoTest = repo)
private val processor = AdProcessor(settings)

fun repoNotFoundTest(command: AdCommand) = runTest {
    val ctx = AdContext(
        command = command,
        state = AdState.NONE,
        workMode = AdWorkMode.TEST,
        adRequest = Ad(
            id = AdId("123456"),
            title = "xyz",
            authors = "xyz",
            publishing = "xyz",
            year = 2025,
            adType = AdType.DEMAND,
            price = BigDecimal.parseString("700.00"),
            lock = AdLock("123-234-abc-ABC")
        )
    )
    processor.exec(ctx)
    assertEquals(AdState.FAILING, ctx.state)
    assertEquals(Ad(), ctx.adResponse)
    assertEquals(1, ctx.errors.size)
    assertNotNull(ctx.errors.find { it.code == "repo-not-found" }, "Errors mus contain not-found")
}