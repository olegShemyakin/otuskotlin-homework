package org.akira.otuskotlin.ads.biz.repo

import org.akira.otuskotlin.ads.biz.exceptions.AdDbNotConfiguredException
import org.akira.otuskotlin.ads.common.AdContext
import org.akira.otuskotlin.ads.common.helpers.errorSystem
import org.akira.otuskotlin.ads.common.helpers.fail
import org.akira.otuskotlin.ads.common.models.AdWorkMode
import org.akira.otuskotlin.ads.common.repo.IRepoAd
import org.akira.otuskotlin.ads.cor.ICorChainDsl
import org.akira.otuskotlin.ads.cor.worker

fun ICorChainDsl<AdContext>.initRepo(title: String) = worker {
    this.title = title
    description = "Установка репозитория в зависимости от режима работы"
    handle {
        adRepo = when {
            workMode == AdWorkMode.TEST -> corSettings.repoTest
            workMode == AdWorkMode.STUB -> corSettings.repoStub
            else -> corSettings.repoProd
        }
        if (workMode != AdWorkMode.STUB && adRepo == IRepoAd.NONE) fail(
            errorSystem(
                violationCode = "dbNotConfigured",
                e = AdDbNotConfiguredException(workMode)
            )
        )
    }
}