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
include(":core-common")
include(":core-model")
include(":core-data")
include(":core-database")
include(":core-datastore")
include(":core-navigation")
include(":core-designsystem")
include(":core-ui")
include(":core-xmpp")
include(":core-testing")
include(":service-xmpp")
include(":feature-router")
include(":feature-auth")
include(":feature-conversations")
include(":feature-chat")
include(":feature-contacts")
