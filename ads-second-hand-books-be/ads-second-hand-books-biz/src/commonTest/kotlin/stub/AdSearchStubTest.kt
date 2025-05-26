package org.akira.otuskotlin.ads.biz.stub

import kotlinx.coroutines.test.runTest
import org.akira.otuskotlin.ads.biz.AdProcessor
import org.akira.otuskotlin.ads.common.AdContext
import org.akira.otuskotlin.ads.common.models.*
import org.akira.otuskotlin.ads.common.stubs.AdsStubs
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import kotlin.test.fail

class AdSearchStubTest {
    private val processor = AdProcessor()
    private val filter = AdFilter(searchString = "Имя ветра")

    @Test
    fun searchSuccess() = runTest {
        val ctx = AdContext(
            command = AdCommand.SEARCH,
            workMode = AdWorkMode.STUB,
            stubCase = AdsStubs.SUCCESS,
            adFilterRequest = filter
        )
        processor.exec(ctx)
        assertTrue(ctx.adsResponse.size > 1)
        val first = ctx.adsResponse.firstOrNull() ?: fail("Empty response list")
        assertTrue(first.title.contains(filter.searchString))
        assertEquals(AdType.DEMAND, first.adType)
    }

    @Test
    fun badSearchString() = runTest {
        val ctx = AdContext(
            command = AdCommand.SEARCH,
            workMode = AdWorkMode.STUB,
            stubCase = AdsStubs.BAD_SEARCH_STRING
        )
        processor.exec(ctx)
        assertEquals(0, ctx.adsResponse.size)
        assertEquals("searchString", ctx.errors.firstOrNull()?.field)
        assertEquals("validation", ctx.errors.firstOrNull()?.group)
    }

    @Test
    fun databaseError() = runTest {
        val ctx = AdContext(
            command = AdCommand.SEARCH,
            workMode = AdWorkMode.STUB,
            stubCase = AdsStubs.DB_ERROR,
            adFilterRequest = filter
        )
        processor.exec(ctx)
        assertEquals(Ad(), ctx.adResponse)
        assertEquals("internal", ctx.errors.firstOrNull()?.group)
    }

    @Test
    fun badNoCase() = runTest {
        val ctx = AdContext(
            command = AdCommand.CREATE,
            workMode = AdWorkMode.STUB,
            stubCase = AdsStubs.BAD_TITLE,
            adFilterRequest = filter
        )
        processor.exec(ctx)
        assertEquals(Ad(), ctx.adResponse)
        assertEquals("validation", ctx.errors.firstOrNull()?.group)
    }
}