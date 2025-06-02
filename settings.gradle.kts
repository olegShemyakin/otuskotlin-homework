pluginManagement {
    plugins {
        val kotlinVersion: String by settings
        kotlin("jvm") version kotlinVersion
    }
}

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.8.0"
}
rootProject.name = "otuskotlin-homework"

includeBuild("lessons")
includeBuild("ads-second-hand-books-be")
includeBuild("ads-second-hand-books-libs")
includeBuild("ads-second-hand-books-other")
