plugins {
    id("build-jvm")
}

repositories {
    google()
    mavenCentral()
}

dependencies {
    implementation(projects.adsSecondHandBooksCommon)
    api(projects.adsSecondHandBooksRepoCommon)

    implementation(libs.coroutines.core)
    implementation(libs.uuid)

    implementation(libs.db.postgres)
    implementation(libs.bundles.exposed)

    implementation(libs.kotlin.bignum)

    testImplementation(kotlin("test-junit"))
    testImplementation(projects.adsSecondHandBooksRepoTest)
    testImplementation(libs.testcontainers.core)
    testImplementation(libs.logback)
}