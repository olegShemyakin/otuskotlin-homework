plugins {
    application
    id("build-jvm")
    alias(libs.plugins.muschko.java)
}

application {
    mainClass.set("org.akira.otuskotlin.ads.app.kafka.MainKt")
}

docker {
    javaApplication {
        baseImage.set("eclipse-temurin:21.0.7_6-jre-jammy")
    }
}

dependencies {
    implementation(libs.kafka.client)
    implementation(libs.coroutines.core)
    implementation(libs.kotlinx.atomicfu)

    implementation("org.akira.otuskotlin.ads.libs:ads-second-hand-books-lib-logging-logback")

    implementation(project(":ads-second-hand-books-app-common"))

    //transport models
    implementation(project(":ads-second-hand-books-common"))
    implementation(project(":ads-second-hand-books-api-jackson"))
    implementation(project(":ads-second-hand-books-api-mappers"))

    implementation(project(":ads-second-hand-books-biz"))

    testImplementation(kotlin("test-junit"))
}