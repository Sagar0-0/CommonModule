plugins {
    alias(libs.plugins.asta.android.library)
}

android {
    namespace = "fit.asta.health.core.test"
    defaultConfig {
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)

    implementation(libs.mockk.android)
    implementation(libs.kotlinx.coroutines.test)
    implementation(libs.junit.jupiter)
}