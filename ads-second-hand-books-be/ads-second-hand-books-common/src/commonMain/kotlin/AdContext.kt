package org.akira.otuskotlin.ads.common

import kotlinx.datetime.Instant
import org.akira.otuskotlin.ads.common.models.*
import org.akira.otuskotlin.ads.common.stubs.AdsStubs

data class AdContext(
    var command: AdCommand = AdCommand.NONE,
    var state: AdState = AdState.NONE,
    val errors: MutableList<AdError> = mutableListOf(),

    var corSettings: AdCorSettings = AdCorSettings(),
    var workMode: AdWorkMode = AdWorkMode.PROD,
    var stubCase: AdsStubs = AdsStubs.NONE,

    var requestId: AdRequestId = AdRequestId.NONE,
    var timeStart: Instant = Instant.NONE,
    var adRequest: Ad = Ad(),
    var adFilterRequest: AdFilter = AdFilter(),

    var adResponse: Ad = Ad(),
    val adsResponse: MutableList<Ad> = mutableListOf()

    )
