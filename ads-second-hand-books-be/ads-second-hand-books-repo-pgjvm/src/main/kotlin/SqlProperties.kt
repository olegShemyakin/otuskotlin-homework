package org.akira.otuskotlin.ads.repo.postgresql

data class SqlProperties(
    val host: String = "localhost",
    val port: Int = 5432,
    val user: String = "postgres",
    val password: String = "postgres",
    val database: String = "ads_db",
    val schema: String = "public",
    val table: String = "ads"
) {
    val url: String
        get() = "jdbc:postgresql://${host}:${port}/${database}"
}
