plugins {
    id("build-kmp")
}

kotlin {
    sourceSets {
        commonMain {
            dependencies {
                api(kotlin("test-common"))
                api(kotlin("test-annotations-common"))

                api(libs.coroutines.core)
                api(libs.coroutines.test)
                api(libs.kotlin.bignum)
                implementation(projects.adsSecondHandBooksCommon)
                implementation(projects.adsSecondHandBooksRepoCommon)
                implementation(projects.adsSecondHandBooksStubs)
            }
        }
        jvmMain {
            dependencies {
                api(kotlin("test-junit"))
            }
        }
    }
}