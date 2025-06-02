//import com.bmuschko.gradle.docker.tasks.image.DockerBuildImage
//import com.bmuschko.gradle.docker.tasks.image.DockerPushImage
//import com.bmuschko.gradle.docker.tasks.image.Dockerfile
import io.ktor.plugin.features.*
import org.jetbrains.kotlin.gradle.tasks.KotlinNativeLink

plugins {
    id("build-kmp")
    alias(libs.plugins.kotlinx.serialization)
    alias(libs.plugins.ktor)
    //alias(libs.plugins.muschko.remote)
    alias(libs.plugins.palantir.docker)
}

application {
    mainClass.set("io.ktor.server.cio.EngineMain")
}

ktor {
    configureNativeImage(project)
    docker {
        localImageName.set(project.name)
        imageTag.set(project.version.toString())
        jreVersion.set(JavaVersion.VERSION_21)
    }
}

jib {
    container.mainClass = application.mainClass.get()
}

docker {
    name = "${project.name}-x64:${project.version}"

    // Файлы для Docker-контекста
    files(
        file("src/commonMain/resources/application.yml"),
    )

    // Путь к Dockerfile.X64 (если не в корне)
    setDockerfile(file("Dockerfile.X64"))

    // Аргументы сборки
    buildArgs(mapOf(
        "APP_VERSION" to project.version.toString()
    ))

    // Лейблы
    labels(mapOf(
        "maintainer" to "dev@example.com"
    ))
}

kotlin {
    // Для сбора uber jar в shadowJar
    jvm { withJava() }
    targets.withType<org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget> {
        binaries {
            executable {
                entryPoint = "org.akira.otuskotlin.ads.app.ktor.main"
            }
        }
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(kotlin("stdlib-common"))
                implementation(libs.ktor.server.core)
                implementation(libs.ktor.server.cio)
                implementation(libs.ktor.server.cors)
                implementation(libs.ktor.server.yaml)
                implementation(libs.ktor.server.negotiation)
                implementation(libs.ktor.server.headers.response)
                implementation(libs.ktor.server.headers.caching)

                implementation(project(":ads-second-hand-books-common"))
                implementation(project(":ads-second-hand-books-app-common"))
                implementation(project(":ads-second-hand-books-biz"))

                //stubs
                implementation(project(":ads-second-hand-books-stubs"))

                implementation(libs.kotlinx.serialization.core)
                implementation(libs.kotlinx.serialization.json)
                implementation(libs.ktor.serialization.json)

                //DB
                implementation(libs.uuid)
                implementation(projects.adsSecondHandBooksRepoCommon)
                implementation(projects.adsSecondHandBooksRepoStubs)
                implementation(projects.adsSecondHandBooksRepoInmemory)

                //logging
                implementation(project(":ads-second-hand-books-api-log"))
                implementation("org.akira.otuskotlin.ads.libs:ads-second-hand-books-lib-logging-common")
            }
        }

        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))

                //DB
                implementation(projects.adsSecondHandBooksRepoCommon)

                implementation(libs.ktor.server.test)
                implementation(libs.ktor.client.negotiation)
            }
        }

        val jvmMain by getting {
            dependencies {
                implementation(kotlin("stdlib-jdk8"))

                //jackson
                implementation(libs.ktor.serialization.jackson)
                implementation(libs.ktor.server.calllogging)
                implementation(libs.ktor.server.headers.default)

                implementation(libs.logback)

                //models
                implementation(project(":ads-second-hand-books-api-jackson"))
                implementation(project(":ads-second-hand-books-api-mappers"))

                implementation("org.akira.otuskotlin.ads.libs:ads-second-hand-books-lib-logging-logback")
                implementation(projects.adsSecondHandBooksRepoPgjvm)
                implementation(libs.testcontainers.postgres)
            }
        }

        val jvmTest by getting {
            dependencies {
                implementation(kotlin("test-junit"))
            }
        }
    }
}

tasks {
    shadowJar {
        isZip64 = true
    }

    // Если ошибка: "Entry application.yaml is a duplicate but no duplicate handling strategy has been set."
    // Возникает из-за наличия файлов как в common, так и в jvm платформе
    withType(ProcessResources::class) {
        duplicatesStrategy = DuplicatesStrategy.EXCLUDE
    }

    val linkReleaseExecutableLinuxX64 by getting(KotlinNativeLink::class)
    val nativeFileX64 = linkReleaseExecutableLinuxX64.binary.outputFile
    val linuxX64ProcessResources by getting(ProcessResources::class)
    dockerPrepare {
        dependsOn(linkReleaseExecutableLinuxX64)
        dependsOn(linuxX64ProcessResources)
        group = "docker"
        this.destinationDir
        doFirst {
            copy {
                from(nativeFileX64)
                from(linuxX64ProcessResources.destinationDir)
                into(this@dockerPrepare.destinationDir)
            }
        }
    }
}