plugins {
    alias(libs.plugins.asta.android.library)
    alias(libs.plugins.asta.android.hilt)
    alias(libs.plugins.asta.android.test.data)
}

android {
    namespace = "fit.asta.health.data.orders"
}

dependencies {

    implementation(project(":core:network"))
    implementation(project(":core:common"))

    implementation(libs.gson)
    implementation(libs.retrofit.core)
    implementation(libs.converter.gson)
}