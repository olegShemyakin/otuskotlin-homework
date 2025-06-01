package org.akira.otuskotlin.ads.biz.validation

import org.akira.otuskotlin.ads.biz.AdProcessor
import org.akira.otuskotlin.ads.common.AdCorSettings
import org.akira.otuskotlin.ads.common.models.AdCommand

abstract class BazeBizValidationTest {
    protected abstract val command: AdCommand
    private val settings by lazy { AdCorSettings() }
    protected val processor by lazy { AdProcessor(settings) }
}