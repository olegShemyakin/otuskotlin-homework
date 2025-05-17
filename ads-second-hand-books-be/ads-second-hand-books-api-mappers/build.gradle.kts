plugins {
    id("build-jvm")
}

group = rootProject.group
version = rootProject.version

dependencies {
    implementation(kotlin("stdlib"))
    implementation(libs.kotlin.bignum)
    implementation(project(":ads-second-hand-books-api-jackson"))
    implementation(project(":ads-second-hand-books-common"))

    testImplementation(kotlin("test-junit"))
}