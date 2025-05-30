plugins {
    kotlin("multiplatform")
}

kotlin {
    jvm {  }
    macosX64 { }
    macosArm64 {  }
    linuxX64 {  }


    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(kotlin("stdlib-common"))
                implementation(project(":ads-second-hand-books-common"))
                implementation(libs.kotlin.bignum)
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
            }
        }
        val jvmMain by getting {
            dependencies {
                implementation(kotlin("stdlib"))
            }
        }
        val jvmTest by getting {
            dependencies {
                implementation(kotlin("test-junit"))
            }
        }
    }
}
