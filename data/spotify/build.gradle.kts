plugins {
    alias(libs.plugins.asta.android.library)
    alias(libs.plugins.asta.android.hilt)
    alias(libs.plugins.asta.android.test.data)
    id("com.google.devtools.ksp")
    id("kotlin-parcelize")
}

android {
    namespace = "fit.asta.health.data.spotify"
    defaultConfig {
        consumerProguardFiles("consumer-rules.pro")
    }
}

dependencies {

    implementation(project(":core:network"))
    implementation(project(":core:common"))

    implementation(libs.gson)
    implementation(libs.retrofit.core)

    // Room Library
    implementation(libs.room.runtime)
    ksp(libs.room.compiler)
    implementation(libs.room.ktx)
    androidTestImplementation(libs.androidx.room.testing)
}