package org.akira.otuskotlin.ads.common.exceptions

import org.akira.otuskotlin.ads.common.models.AdCommand

class UnknownAdCommand(command: AdCommand,) : Throwable("Wrong command: $command at mapping toTransport stage.") {
}