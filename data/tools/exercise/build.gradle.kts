@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.asta.android.library)
    alias(libs.plugins.asta.android.hilt)
    alias(libs.plugins.asta.android.test.data)
    id("com.google.devtools.ksp")
    id("kotlin-parcelize")
}

android {
    namespace = "fit.asta.health.data.exercise"
}
dependencies {

    implementation(project(":core:network"))
    implementation(project(":core:common"))
    implementation(project(":core:datastore"))
    implementation(project(":resources:strings"))
    implementation(project(":resources:drawables"))

    implementation(libs.gms.play.services.fitness)
    implementation(libs.gms.play.services.auth)
    implementation(libs.gson)
    implementation(libs.retrofit.core)
    implementation(libs.converter.gson)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.lifecycle.extensions)

    implementation(libs.androidx.media3.common)
    // Room Library
    implementation(libs.room.runtime)
    ksp(libs.room.compiler)
    implementation(libs.room.ktx)
    androidTestImplementation(libs.androidx.room.testing)
}