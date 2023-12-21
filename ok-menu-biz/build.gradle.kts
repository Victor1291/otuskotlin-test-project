plugins {
    kotlin("multiplatform")
}

kotlin {
    jvm {}
    macosX64 {}
    macosArm64 {}
    linuxX64 {}

    sourceSets {
        val coroutinesVersion: String by project

        all { languageSettings.optIn("kotlin.RequiresOptIn") }

        commonMain {
            dependencies {
                implementation(kotlin("stdlib-common"))

                implementation(project(":ok-menu-common"))
                implementation(project(":ok-menu-stubs"))
            }
        }

        commonTest {
            dependencies {
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))

                api("org.jetbrains.kotlinx:kotlinx-coroutines-test:$coroutinesVersion")
            }
        }

        jvmMain {
            dependencies {
                implementation(kotlin("stdlib-jdk8"))
            }
        }

        jvmTest  {
            dependencies {
                implementation(kotlin("test-junit"))
            }
        }
    }
}
