plugins {
    alias(libs.plugins.asta.android.library)
    alias(libs.plugins.asta.android.hilt)
    alias(libs.plugins.asta.android.test.data)
    id("kotlin-parcelize")
}

android {
    namespace = "fit.asta.health.data.payment"
}

dependencies {

    implementation(project(":resources:strings"))

    implementation(project(":core:network"))
    implementation(project(":core:common"))

    implementation(libs.gson)
    implementation(libs.retrofit.core)
    implementation(libs.converter.gson)
}