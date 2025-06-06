rootProject.name = "ads-second-hand-books-be"

dependencyResolutionManagement {
    versionCatalogs {
        create("libs") {
            from(files("../gradle/libs.versions.toml"))
        }
    }
}

pluginManagement {
    includeBuild("../build-plugin")
    plugins {
        id("build-jvm") apply false
        id("build-kmp") apply false
    }
    repositories {
        mavenCentral()
        gradlePluginPortal()
    }
}

/*plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.8.0"
}*/

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

include(":ads-second-hand-books-api-jackson")
include(":ads-second-hand-books-common")
include(":ads-second-hand-books-api-mappers")
include(":ads-second-hand-books-api-log")

include(":ads-second-hand-books-stubs")
include(":ads-second-hand-books-biz")
include(":ads-second-hand-books-app-common")

include(":ads-second-hand-books-ktor")
include(":ads-second-hand-books-kafka")

//DB
include(":ads-second-hand-books-repo-common")
include(":ads-second-hand-books-repo-inmemory")
include(":ads-second-hand-books-repo-test")
include(":ads-second-hand-books-repo-stubs")
include(":ads-second-hand-books-repo-pgjvm")