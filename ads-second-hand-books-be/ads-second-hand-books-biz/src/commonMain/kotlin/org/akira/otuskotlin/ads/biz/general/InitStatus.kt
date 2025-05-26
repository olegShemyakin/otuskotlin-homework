package org.akira.otuskotlin.ads.biz.general

import org.akira.otuskotlin.ads.common.AdContext
import org.akira.otuskotlin.ads.common.models.AdState
import org.akira.otuskotlin.ads.cor.ICorChainDsl
import org.akira.otuskotlin.ads.cor.worker

fun ICorChainDsl<AdContext>.initStatus(title: String) = worker {
    this.title = title
    this.description = "Обработчик устанавливает стартовый статус обработки. Запускается в случае не заданного статуса"
    on { state == AdState.NONE }
    handle { state = AdState.RUNNING }
}