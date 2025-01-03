plugins {
    kotlin("jvm")
}

val coroutinesVersion: String by project

dependencies {
    implementation(kotlin("stdlib"))
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion")

    // Homework Hard
    implementation("com.squareup.okhttp3:okhttp:4.12.0") // http client
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.18.2") // from string to object
    implementation("com.google.guava:guava:33.4.0-jre")

    testImplementation("org.jetbrains.kotlin:kotlin-test")
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(17)
}