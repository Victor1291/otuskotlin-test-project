pluginManagement {
    val kotlinVersion: String by settings
    val kotestVersion: String by settings
    val openapiVersion: String by settings
    val cwpGeneratorVersioin: String by settings

    repositories {
        mavenCentral()
        gradlePluginPortal()
    }

    plugins {
        kotlin("jvm") version kotlinVersion
        kotlin("multiplatform") version kotlinVersion
        kotlin("plugin.serialization") version kotlinVersion apply false

        id("io.kotest.multiplatform") version kotestVersion apply false
        id("org.openapi.generator") version openapiVersion apply false
        id("com.crowdproj.generator") version cwpGeneratorVersioin apply false

    }


}

rootProject.name = "otuskotlin-test-project"

//include("m1l1-first-app")
//include("m1l2-basic")
//include("m1l3-oop")
//include("m1l4-dsl")
//include("m1l5-coroutines")
//include("m1l6-flows")
//include("m1l7-kmp")

include("ok-menu-api-v1-jackson")
include("ok-menu-api-v2-kmp")
include("ok-menu-common")
include("ok-menu-mappers-v1")
include("ok-menu-mappers-v2")
