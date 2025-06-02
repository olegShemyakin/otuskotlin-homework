package org.akira.otuskotlin.ads.repo.common

import org.akira.otuskotlin.ads.common.models.Ad
import org.akira.otuskotlin.ads.common.repo.IRepoAd

interface IRepoAdInitializable : IRepoAd {
    fun save(ads: Collection<Ad>) : Collection<Ad>
}