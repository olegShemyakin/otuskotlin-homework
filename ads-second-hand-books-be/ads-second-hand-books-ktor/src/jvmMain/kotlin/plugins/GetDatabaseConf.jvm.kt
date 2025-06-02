package org.akira.otuskotlin.ads.app.ktor.plugins

import configs.ConfigPaths
import configs.PostgresConfig
import io.ktor.server.application.*
import org.akira.otuskotlin.ads.common.repo.IRepoAd
import org.akira.otuskotlin.ads.repo.postgresql.RepoAdSql
import org.akira.otuskotlin.ads.repo.postgresql.SqlProperties

actual fun Application.getDatabaseConf(type: AdDbType): IRepoAd {
    val dbSettingPath = "${ConfigPaths.repository}.${type.confName}"
    val dbSetting = environment.config.propertyOrNull(dbSettingPath)?.getString()?.lowercase()
    return when (dbSetting) {
        "in-memory", "inmemory", "memory", "mem" -> initInMemory()
        "postgres", "postgresql", "pg", "sql", "psql" -> initPostgres()
        else -> throw IllegalArgumentException(
            "$dbSettingPath must be set in application.yaml to one of: " +
                    "'inmemory', 'postgres', 'cassandra', 'gremlin'"
        )
    }
}

fun Application.initPostgres(): IRepoAd {
    val config = PostgresConfig(environment.config)
    return RepoAdSql(
        properties = SqlProperties(
            host = config.host,
            port = config.port,
            user = config.user,
            password = config.password,
            schema = config.schema,
            database = config.database,
        ),
    )
}