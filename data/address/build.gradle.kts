plugins {
    alias(libs.plugins.asta.android.library)
    alias(libs.plugins.asta.android.hilt)
    alias(libs.plugins.asta.android.test.data)
}

android {
    namespace = "fit.asta.health.data.address"
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

    implementation(project(":resources:strings"))

    implementation(project(":core:network"))
    implementation(project(":core:common"))
    implementation(project(":core:datastore"))

    implementation(libs.gson)
    implementation(libs.retrofit.core)
    implementation(libs.converter.gson)

    implementation(libs.play.services.maps)
    implementation(libs.play.services.location)
}