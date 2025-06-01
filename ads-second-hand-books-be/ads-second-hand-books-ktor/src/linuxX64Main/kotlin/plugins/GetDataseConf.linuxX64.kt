package org.akira.otuskotlin.ads.app.ktor.plugins

import io.ktor.server.application.*
import org.akira.otuskotlin.ads.common.repo.IRepoAd

actual fun Application.getDatabaseConf(type: AdDbType): IRepoAd {
    TODO("Not yet implemented")
}