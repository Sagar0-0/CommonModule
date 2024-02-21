plugins {
    alias(libs.plugins.asta.android.library)
    alias(libs.plugins.asta.android.hilt)
    alias(libs.plugins.asta.android.test.data)
    id("kotlin-parcelize")
    id("com.google.devtools.ksp")
}

android {
    namespace = "fit.asta.health.data.profile"
}

dependencies {

    implementation(project(":core:network"))
    implementation(project(":core:common"))
    implementation(project(":core:datastore"))

    implementation(libs.gson)
    implementation(libs.retrofit.core)
    implementation(libs.converter.gson)

    // Room Library
    implementation(libs.room.runtime)
    ksp(libs.room.compiler)
    implementation(libs.room.ktx)
    androidTestImplementation(libs.androidx.room.testing)
}