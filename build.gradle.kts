import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") apply false
}

group = "ru.otus.otuskotlin"
version = "0.0.1"

repositories {
    mavenCentral()
    google()
}

subprojects {
    group = rootProject.group
    version = rootProject.version

    repositories {
        mavenCentral()
        google()
    }
    tasks.withType<KotlinCompile> {
        kotlinOptions.jvmTarget = "17"
    }
}