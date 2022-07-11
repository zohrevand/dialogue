plugins {
    id("dialogue.android.library")
    kotlin("kapt")
    id("dagger.hilt.android.plugin")
    id("dialogue.spotless")
}

dependencies {
    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)
}