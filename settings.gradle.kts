pluginManagement {
    includeBuild("build-logic")
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}
rootProject.name = "dialogue"
include(":app")
include(":core-model")
include(":core-data")
include(":core-database")
include(":core-xmpp")
include(":core-common")
include(":core-navigation")
include(":feature-auth")
include(":feature-conversations")
include(":feature-chat")
include(":feature-contacts")
include(":core-datastore")
include(":feature-router")
include(":core-designsystem")
include(":core-testing")
