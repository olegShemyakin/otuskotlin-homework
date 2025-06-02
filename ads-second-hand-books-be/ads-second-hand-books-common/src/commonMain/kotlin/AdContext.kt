package org.akira.otuskotlin.ads.common

import kotlinx.datetime.Instant
import org.akira.otuskotlin.ads.common.models.*
import org.akira.otuskotlin.ads.common.repo.IRepoAd
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

    var adValidating: Ad = Ad(),
    var adFilterValidating: AdFilter = AdFilter(),

    var adValidated: Ad = Ad(),
    var adFilterValidated: AdFilter = AdFilter(),

    var adRepo: IRepoAd = IRepoAd.NONE,
    var adRepoRead: Ad = Ad(),
    var adRepoPrepare: Ad = Ad(),
    var adRepoDone: Ad = Ad(),
    var adsRepoDone: MutableList<Ad> = mutableListOf(),

    var adResponse: Ad = Ad(),
    var adsResponse: MutableList<Ad> = mutableListOf()

    )
