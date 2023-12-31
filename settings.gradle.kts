rootProject.name = "otuskotlin-test-project"

pluginManagement {
    val kotlinVersion: String by settings
    val kotestVersion: String by settings
    val openapiVersion: String by settings
    val cwpGeneratorVersioin: String by settings
    val springframeworkBootVersion: String by settings
    val springDependencyManagementVersion: String by settings
    //spring
    val pluginJpa: String by settings


    repositories {
        mavenCentral()
        gradlePluginPortal()
    }

    plugins {
        kotlin("jvm") version kotlinVersion
        kotlin("multiplatform") version kotlinVersion
        kotlin("plugin.serialization") version kotlinVersion apply false

        id("org.springframework.boot") version springframeworkBootVersion apply false
        id("io.spring.dependency-management") version springDependencyManagementVersion apply false
        kotlin("plugin.spring") version kotlinVersion apply false
//spring
        kotlin("plugin.jpa") version pluginJpa apply false

        id("io.kotest.multiplatform") version kotestVersion apply false
        id("org.openapi.generator") version openapiVersion apply false
        id("com.crowdproj.generator") version cwpGeneratorVersioin apply false

    }


}



//include("m1l1-first-app")
//include("m1l2-basic")
//include("m1l3-oop")
//include("m1l4-dsl")
//include("m1l5-coroutines")
//include("m1l6-flows")
//include("m1l7-kmp")

include("m3l1-spring")

include("ok-menu-api-v1-jackson")
include("ok-menu-mappers-v1")

include("ok-menu-api-v2-kmp")
include("ok-menu-mappers-v2")

include("ok-menu-stubs")

include("ok-menu-common")

include("ok-menu-biz")

include("ok-menu-app-spring")

