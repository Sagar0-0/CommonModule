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
include(":libs:chart")
include(":libs:media")
include(":mobile")

include(":resources:strings")
include(":resources:drawables")

include(":core:common")
include(":core:datastore")
include(":core:network")
include(":core:designsystem")
include(":core:ui")
include(":core:test")

include(":data:address")
include(":data:auth")
include(":data:feedback")
include(":data:onboarding")
include(":data:payments:orders")
include(":data:payments:payment")
include(":data:payments:offers")
include(":data:profile")
include(":data:payments:referral")
include(":data:payments:discounts")
include(":data:scheduler")
include(":data:payments:subscription")
include(":data:testimonials")
include(":data:payments:wallet")
include(":data:spotify")
include(":data:meditation")

include(":feature:address")
include(":feature:auth")
include(":feature:feedback")
include(":feature:onboarding")
include(":feature:payments:orders")
include(":feature:payments:payment")
include(":feature:profile")
include(":feature:payments:referral")
include(":feature:scheduler")
include(":feature:settings")
include(":feature:payments:subscription")
include(":feature:spotify")
include(":feature:testimonials")
include(":feature:payments:wallet")
include(":feature:meditation")
include(":feature:payments:offers")
include(":feature:payments:discounts")
