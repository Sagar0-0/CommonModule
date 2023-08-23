plugins {
    alias(libs.plugins.asta.android.library)
    alias(libs.plugins.asta.android.hilt)
}

android {
    namespace = "fit.asta.health.data.onboarding"
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

    implementation(project(":core:network"))
    implementation(project(":core:common"))
    implementation(project(":core:datastore"))

    implementation(libs.gson)
    implementation(libs.retrofit.core)
}