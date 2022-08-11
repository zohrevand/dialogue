plugins {
    id("dialogue.android.library")
    id("dialogue.android.library.jacoco")
    kotlin("kapt")
    id("dialogue.spotless")
}

dependencies {
    implementation(libs.kotlinx.coroutines.android)

    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)
}