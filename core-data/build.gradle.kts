plugins {
    id("dialogue.android.library")
    kotlin("kapt")
    id("dialogue.spotless")
}

dependencies {
    implementation(project(":core-model"))

    implementation(libs.kotlinx.coroutines.android)

    testImplementation(libs.junit4)
    testImplementation(libs.kotlinx.coroutines.test)
}