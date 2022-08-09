import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType

class AndroidFeatureConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply {
                apply("com.android.library")
                apply("org.jetbrains.kotlin.android")
                apply("org.jetbrains.kotlin.kapt")
            }

            val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")

            dependencies {
                add("implementation", project(":core-model"))
                add("implementation", project(":core-data"))
                add("implementation", project(":core-common"))
                add("implementation", project(":core-designsystem"))
                add("implementation", project(":core-navigation"))

                add("testImplementation", project(":core-testing"))
                add("androidTestImplementation", project(":core-testing"))

                add("api", libs.findLibrary("androidx.compose.foundation").get())
                add("api", libs.findLibrary("androidx.compose.foundation.layout").get())
                add("api", libs.findLibrary("androidx.compose.material.iconsExtended").get())
                add("api", libs.findLibrary("androidx.compose.material3").get())
                add("debugApi", libs.findLibrary("androidx.compose.ui.tooling").get())
                add("api", libs.findLibrary("androidx.compose.ui.tooling.preview").get())
                add("api", libs.findLibrary("androidx.compose.ui.util").get())
                add("api", libs.findLibrary("androidx.compose.runtime").get())

                add("implementation", libs.findLibrary("androidx.hilt.navigation.compose").get())
                add("implementation", libs.findLibrary("androidx.lifecycle.runtimeCompose").get())
                add("implementation", libs.findLibrary("androidx.lifecycle.viewModelCompose").get())

                add("implementation", libs.findLibrary("kotlinx.coroutines.android").get())
                add("implementation", libs.findLibrary("kotlinx.datetime").get())

                add("implementation", libs.findLibrary("hilt.android").get())
                add("kapt", libs.findLibrary("hilt.compiler").get())

                // TODO: Remove this dependency once we upgrade to Android Studio Dolphin
                // These dependencies are currently necessary to render Compose previews
                add(
                    "debugImplementation",
                    libs.findLibrary("androidx.customview.poolingcontainer").get()
                )
            }
        }
    }
}