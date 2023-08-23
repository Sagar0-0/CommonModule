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
include(":resources:strings")
include(":resources:drawables")
include(":feature:auth")
include(":core:common")
include(":core:network")
include(":core:designsystem")
include(":core:datastore")
include(":chartLibrary")
include(":mobile")

include(":data:auth")
include(":data:payment")
include(":data:referral")
include(":data:subscription")
include(":data:wallet")

include(":feature:payment")
include(":feature:referral")
include(":feature:subscription")
include(":feature:wallet")
include(":data:onboarding")
include(":feature:onboarding")
