plugins {
    alias(libs.plugins.asta.android.library)
    alias(libs.plugins.asta.android.hilt)
    alias(libs.plugins.asta.android.test.data)
    id("com.google.devtools.ksp")
    id("kotlin-parcelize")
}

android {
    namespace = "fit.asta.health.data.scheduler"
    defaultConfig {
        consumerProguardFiles("consumer-rules.pro")
    }
}
dependencies {

    implementation(project(":core:network"))
    implementation(project(":core:common"))
    implementation(project(":resources:strings"))
    implementation(project(":resources:drawables"))

    implementation(libs.gson)
    implementation(libs.retrofit.core)
    implementation(libs.converter.gson)
    // Room Library
    implementation(libs.room.runtime)
    ksp(libs.room.compiler)
    implementation(libs.room.ktx)
    androidTestImplementation(libs.androidx.room.testing)
}