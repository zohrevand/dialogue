plugins {
    id("dialogue.android.library")
    kotlin("kapt")
    id("dagger.hilt.android.plugin")
    id("dialogue.spotless")
}

dependencies {
    implementation(project(":core-data"))

    implementation(libs.kotlinx.coroutines.android)

    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)
}