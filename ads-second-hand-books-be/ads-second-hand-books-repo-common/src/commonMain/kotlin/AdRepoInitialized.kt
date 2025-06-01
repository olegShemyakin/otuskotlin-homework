package org.akira.otuskotlin.ads.repo.common

import org.akira.otuskotlin.ads.common.models.Ad

/**
 * Делегат для всех репозиториев, позволяющий инициализировать базу данных предзагруженными данными
 */
class AdRepoInitialized(
    val repo: IRepoAdInitializable,
    initObjects: Collection<Ad> = emptyList()
) : IRepoAdInitializable by repo{
    val initializedObjects: List<Ad> = save(initObjects).toList()
}