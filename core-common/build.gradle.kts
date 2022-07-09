plugins {
    id("dialogue.android.library")
    kotlin("kapt")
    id("dialogue.spotless")
}

dependencies {
    implementation(libs.kotlinx.coroutines.android)

    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)
}