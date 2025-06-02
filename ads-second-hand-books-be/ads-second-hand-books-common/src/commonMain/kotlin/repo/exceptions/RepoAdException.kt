package org.akira.otuskotlin.ads.common.repo.exceptions

import org.akira.otuskotlin.ads.common.models.AdId

open class RepoAdException(
    val adId: AdId,
    msg: String
) : RepoException(msg)