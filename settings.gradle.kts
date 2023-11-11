pluginManagement {
    repositories {
        mavenCentral()
        gradlePluginPortal()
    }

    plugins {
        val kotlinVersion: String by settings

        kotlin("jvm") version kotlinVersion
    }


}

rootProject.name = "otuskotlin-test-project"

include("m1l1-first-app")
include("m1l2-basic")
include("m1l3-oop")
include("m1l4-dsl")
