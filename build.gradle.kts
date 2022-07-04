buildscript {
    extra.apply{
        set("compose_version", "1.2.0-rc03")
        set("compose_compiler_version", "1.2.0")
    }

    repositories {
        google()
        mavenCentral()
    }

    dependencies {
        classpath(libs.android.gradlePlugin)
        classpath(libs.kotlin.gradlePlugin)
    }
}
