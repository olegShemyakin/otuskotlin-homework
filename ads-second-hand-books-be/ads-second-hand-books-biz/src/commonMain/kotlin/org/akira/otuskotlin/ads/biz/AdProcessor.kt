package org.akira.otuskotlin.ads.biz

import org.akira.otuskotlin.ads.common.AdContext
import org.akira.otuskotlin.ads.common.AdCorSettings
import org.akira.otuskotlin.ads.common.models.AdState
import org.akira.otuskotlin.ads.common.models.AdType
import org.akira.otuskotlin.ads.stubs.AdStub

@Suppress("unused", "RedundantSuspendModifier")
class AdProcessor(val corSettings: AdCorSettings) {
    suspend fun exec(ctx: AdContext) {
        ctx.adResponse = AdStub.get()
        ctx.adsResponse.addAll(AdStub.prepareSearchList("ad search", AdType.DEMAND))
        ctx.state = AdState.RUNNING
    }
}