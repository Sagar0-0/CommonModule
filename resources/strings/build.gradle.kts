plugins {
    alias(libs.plugins.asta.android.library)
}
android {
    namespace = "fit.asta.health.resources.strings"

    defaultConfig {
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }
}