plugins {
    id("dialogue.android.library")
    kotlin("kapt")
    id("dagger.hilt.android.plugin")
    id("dialogue.spotless")
}

dependencies {
    implementation(project(":core-data"))

    implementation(libs.smack.tcp)
    api(libs.smack.android.extensions)

    implementation(libs.kotlinx.coroutines.android)

    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)

    // smack xpp3 exclusion
    configurations {
        all {
            exclude(group = "xpp3", module = "xpp3")
        }
    }
}