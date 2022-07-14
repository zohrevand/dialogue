plugins {
    id("dialogue.android.library")
    kotlin("kapt")
    id("dagger.hilt.android.plugin")
    id("dialogue.spotless")
}

dependencies {
    implementation(project(":core-database"))
    implementation(project(":core-model"))
    implementation(project(":core-datastore"))

    implementation(libs.kotlinx.coroutines.android)

    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)

    testImplementation(libs.junit4)
    testImplementation(libs.kotlinx.coroutines.test)
}