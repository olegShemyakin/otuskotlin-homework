package configs

import io.ktor.server.config.*

data class PostgresConfig(
    val host: String = "localhost",
    val port: Int = 5432,
    val user: String = "postgres",
    val password: String = "postgres",
    val database: String = "ads_db",
    val schema: String = "public"
) {
    constructor(config: ApplicationConfig): this(
        host = config.propertyOrNull("$PATH.host")?.getString() ?: "localhost",
        port = config.propertyOrNull("$PATH.port")?.getString()?.toIntOrNull() ?: 5432,
        user = config.propertyOrNull("$PATH.user")?.getString() ?: "postgres",
        password = config.property("$PATH.password").getString(),
        database = config.propertyOrNull("$PATH.database")?.getString() ?: "ads_db",
        schema = config.propertyOrNull("$PATH.schema")?.getString() ?: "public",
    )

    companion object {
        const val PATH = "${ConfigPaths.repository}.psql"
    }
}
