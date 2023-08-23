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
    testOptions {
        unitTests {
            this.all {
                it.useJUnitPlatform()
            }
            isIncludeAndroidResources = true
        }
    }
}

dependencies {

    implementation(project(":core:network"))
    implementation(project(":core:common"))
    implementation(project(":core:datastore"))

    implementation(libs.gson)
    implementation(libs.retrofit.core)
    implementation(libs.converter.gson)

    testImplementation(libs.mockk.android)
    testImplementation(libs.turbine)
    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.mockwebserver)
    testImplementation(libs.junit.jupiter)
}