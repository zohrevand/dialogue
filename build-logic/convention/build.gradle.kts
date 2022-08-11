plugins {
    `kotlin-dsl`
}

group = "io.github.zohrevand.dialogue.buildlogic"

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

dependencies {
    implementation(libs.android.gradlePlugin)
    implementation(libs.kotlin.gradlePlugin)
    implementation(libs.spotless.gradlePlugin)
}

gradlePlugin {
    plugins {
        register("androidApplication") {
            id = "dialogue.android.application"
            implementationClass = "AndroidApplicationConventionPlugin"
        }
        register("androidLibrary") {
            id = "dialogue.android.library"
            implementationClass = "AndroidLibraryConventionPlugin"
        }
        register("androidApplicationCompose") {
            id = "dialogue.android.application.compose"
            implementationClass = "AndroidApplicationComposeConventionPlugin"
        }
        register("androidLibraryCompose") {
            id = "dialogue.android.library.compose"
            implementationClass = "AndroidLibraryComposeConventionPlugin"
        }
        register("androidFeature") {
            id = "dialogue.android.feature"
            implementationClass = "AndroidFeatureConventionPlugin"
        }
        register("spotless") {
            id = "dialogue.spotless"
            implementationClass = "SpotlessConventionPlugin"
        }
        register("androidApplicationJacoco") {
            id = "dialogue.android.application.jacoco"
            implementationClass = "AndroidApplicationJacocoConventionPlugin"
        }
        register("androidLibraryJacoco") {
            id = "dialogue.android.library.jacoco"
            implementationClass = "AndroidLibraryJacocoConventionPlugin"
        }
    }
}
