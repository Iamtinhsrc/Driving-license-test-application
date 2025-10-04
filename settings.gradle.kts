pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
rootProject.name = "Driving_Car_Project"
include(":app")
include(":core-model")
include(":core-database")
include(":core-network")
include(":core-utils")
include(":core-di")
include(":core-ui")
include(":core-navigation")
include(":infrastructure")
include(":feature-question")
include(":feature-exam")
include(":feature-category")
include(":feature-guide")
include(":feature-home")
include(":core-navigation")
include(":core-resources")
include(":feature-history")
