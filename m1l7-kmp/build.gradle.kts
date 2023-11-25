plugins {
    kotlin("multiplatform")
}

repositories {
    mavenCentral()
}

val coroutinesVersion: String by project
val datetimeVersion: String by project

kotlin {
    jvm {
        compilations.all {
            kotlinOptions.jvmTarget = "17"
        }
        /*
        withJava() - важная опция
         */
        withJava()
        testRuns["test"].executionTask.configure {
            useJUnitPlatform()
        }
    }
    js {
        browser{
          /*  testTask {
                useKarma {
                    // Выбираем браузеры, на которых будет тестироваться
                    useChrome()
                    useFirefox()
                }
                // Без этой настройки длительные тесты не отрабатывают
                useMocha {
                    timeout = "100s"
                }
            }*/
        }
    }

    /*linuxX64 {

    }

    linuxArm64 {

    }
    macosArm64 {

    }*/

    listOf(
        linuxX64(),
        //linuxArm64(),
        macosArm64(),
    ).forEach {
        it.apply {
            compilations.getByName("main") {
                cinterops {
                    // настраиваем cinterop в файле src/nativeInterop/cinterop/libcurl.def
                    val libcurl by creating {
                        includeDirs {
                            allHeaders("/usr/include/x86_64-linux-gnu", "/usr/include")
                        }
                    }
                }
            }
            binaries {
                executable {
                    entryPoint = "main"
                }
            }
        }
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(kotlin("stdlib-common"))
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion")
                implementation("org.jetbrains.kotlinx:kotlinx-datetime:$datetimeVersion")
            }
        }
        commonTest {
            dependencies {
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:$coroutinesVersion")
            }
        }
        val jvmMain by getting
        val jvmTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }

        // dependencies from npm
        val jsMain by getting {
            dependencies {
                implementation(npm("js-big-decimal", "~1.3.4"))
                implementation(npm("is-sorted", "~1.0.5"))
            }
        }
        val jsTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }

        // С 1.9.20 можно так
        nativeMain {
        }
        nativeTest {
        }

    }

}


/*
dependencies {
    implementation(kotlin("stdlib"))
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion")



    testImplementation(kotlin("test-junit"))
}
*/

