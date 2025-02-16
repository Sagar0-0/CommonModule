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
include(":libs:otpfield")
include(":libs:ccp")
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
include(":data:profile")
include(":data:scheduler")
include(":data:testimonials")
include(":data:spotify")
include(":data:home")
include(":data:payments:orders")
include(":data:payments:payment")
include(":data:payments:offers")
include(":data:payments:referral")
include(":data:payments:discounts")
include(":data:payments:subscription")
include(":data:payments:wallet")
include(":data:tools:breathing")
include(":data:tools:walking")
include(":data:tools:meditation")
include(":data:tools:exercise")
include(":data:tools:sunlight")
include(":data:tools:sleep")
include(":data:tools:water")

include(":feature:address")
include(":feature:auth")
include(":feature:feedback")
include(":feature:profile")
include(":feature:settings")
include(":feature:scheduler")
include(":feature:spotify")
include(":feature:testimonials")
include(":feature:payments:offers")
include(":feature:payments:orders")
include(":feature:payments:payment")
include(":feature:payments:referral")
include(":feature:payments:subscription")
include(":feature:payments:wallet")
include(":feature:payments:discounts")
include(":feature:tools:breathing")
include(":feature:tools:walking")
include(":feature:tools:meditation")
include(":feature:tools:water")
include(":feature:tools:exercise")
include(":feature:tools:sunlight")
include(":feature:tools:sleep")
