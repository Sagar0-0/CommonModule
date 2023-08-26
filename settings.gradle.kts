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

include(":libs:spotify-app-remote")
include(":chartLibrary")
include(":mobile")

include(":resources:strings")
include(":resources:drawables")

include(":core:common")
include(":core:datastore")
include(":core:designsystem")
include(":core:network")
include(":core:test")

include(":data:address")
include(":data:auth")
include(":data:feedback")
include(":data:onboarding")
include(":data:orders")
include(":data:payment")
include(":data:profile")
include(":data:referral")
include(":data:subscription")
include(":data:wallet")

include(":feature:address")
include(":feature:auth")
include(":feature:feedback")
include(":feature:onboarding")
include(":feature:orders")
include(":feature:payment")
include(":feature:profile")
include(":feature:referral")
include(":feature:settings")
include(":feature:subscription")
include(":feature:wallet")
include(":data:spotify")
include(":feature:spotify")
