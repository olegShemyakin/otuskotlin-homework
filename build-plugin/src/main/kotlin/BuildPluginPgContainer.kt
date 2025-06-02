package org.akira.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.containers.wait.strategy.Wait

internal class BuildPluginPgContainer : Plugin<Project> {
    val pgDbName = "ads_db"
    val pgUsername = "postgres"
    val pgPassword = "postgres"

    private val pgContainer = PostgreSQLContainer<Nothing>("postgres:latest").apply {
        withUsername(pgUsername)
        withPassword(pgPassword)
        withDatabaseName(pgDbName)
        waitingFor(Wait.forLogMessage("database system is ready to accept connections", 1))
    }


    override fun apply(project: Project): Unit = with(project) {
        val stopTask = tasks.register("pgStop") {
            group = "containers"
            pgContainer.stop()
        }
        tasks.register("pgStart", PgContainerStartTask::class.java) {
            pgContainer.start()
//            port = pgContainer.getMappedPort(5432)
            pgUrl = pgContainer.jdbcUrl
            finalizedBy(stopTask)
        }
    }
}