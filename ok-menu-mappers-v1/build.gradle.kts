plugins {
    kotlin("jvm")
}

group = rootProject.group
version = rootProject.version

dependencies {
    implementation(kotlin("stdlib"))
    implementation(project(":ok-menu-api-v1-jackson"))
    implementation(project(":ok-menu-common"))

    testImplementation(kotlin("test-junit"))
}
