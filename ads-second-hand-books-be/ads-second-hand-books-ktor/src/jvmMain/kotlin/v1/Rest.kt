package v1

import AdAppSettings
import io.ktor.server.application.*
import io.ktor.server.routing.*
import org.akira.otuskotlin.ads.app.ktor.v1.*

fun Route.v1Ad(appSettings: AdAppSettings) {
    route("ad") {
        post("create") {
            call.createAd(appSettings)
        }
        post("read") {
            call.readAd(appSettings)
        }
        post("update") {
            call.updateAd(appSettings)
        }
        post("delete") {
            call.deleteAd(appSettings)
        }
        post("search") {
            call.searchAd(appSettings)
        }
    }
}