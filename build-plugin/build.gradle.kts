plugins {
    `kotlin-dsl`
}

gradlePlugin {
    plugins {
        register("build-jvm") {
            id = "build-jvm"
            implementationClass = "org.akira.plugin.BuildPluginJvm"
        }
        register("build-kmp") {
            id = "build-kmp"
            implementationClass = "org.akira.plugin.BuildPluginMultiplatform"
        }
        register("build-pgContainer") {
            id = "build-pgContainer"
            implementationClass = "org.akira.plugin.BuildPluginPgContainer"
        }
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(files(libs.javaClass.superclass.protectionDomain.codeSource.location))
    implementation(libs.plugin.kotlin)
    implementation(libs.plugin.binaryCompatibilityValidator)

    implementation(libs.testcontainers.postgres)
    implementation(libs.testcontainers.core)
    implementation(libs.db.postgres)
}