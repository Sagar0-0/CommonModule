pluginManagement {
    includeBuild("build-logic")
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
        maven(url = "https://jitpack.io")
        maven(url = "https://oss.sonatype.org/content/repositories/snapshots/")
    }
}

rootProject.name = "asta"
include(":mobile")
include(":chartLibrary")
include(":feature:subscription")
include(":feature:wallet")
include(":feature:referral")
include(":data:subscription")
include(":data:wallet")
include(":data:referral")
include(":core:network")
include(":core:common")
include(":feature:payment")
include(":data:payment")
include(":data:auth")
