package org.akira.otuskotlin.ads.app.common

import org.akira.otuskotlin.ads.biz.AdProcessor
import org.akira.otuskotlin.ads.common.AdCorSettings

interface IAdAppSettings {
    val processor: AdProcessor
    val corSettings: AdCorSettings
}