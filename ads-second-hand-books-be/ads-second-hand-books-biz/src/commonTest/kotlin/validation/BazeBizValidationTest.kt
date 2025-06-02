package org.akira.otuskotlin.ads.biz.validation

import org.akira.otuskotlin.ads.biz.AdProcessor
import org.akira.otuskotlin.ads.common.AdCorSettings
import org.akira.otuskotlin.ads.common.models.AdCommand
import org.akira.otuskotlin.ads.repo.common.AdRepoInitialized
import org.akira.otuskotlin.ads.repo.inmemory.AdRepoInMemory
import org.akira.otuskotlin.ads.stubs.AdStub

abstract class BazeBizValidationTest {
    protected abstract val command: AdCommand
    private val repo = AdRepoInitialized(
        repo = AdRepoInMemory(),
        initObjects = listOf(
            AdStub.get()
        )
    )
    private val settings by lazy { AdCorSettings(repoTest = repo) }
    protected val processor by lazy { AdProcessor(settings) }
}