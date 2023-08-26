plugins {
    alias(libs.plugins.asta.android.library)
    alias(libs.plugins.asta.android.hilt)
    id("com.google.android.libraries.mapsplatform.secrets-gradle-plugin")
}

secrets {
    defaultPropertiesFileName = "secrets.properties"
}

android {
    namespace = "fit.asta.health.core.network"

    buildFeatures {
        buildConfig = true
    }
}

dependencies {

    implementation(project(mapOf("path" to ":core:common")))

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)

    testImplementation(libs.junit4)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.test.espresso.core)

    // Retrofit and OkHttp
    implementation(libs.okhttp)
    implementation(libs.okhttp.logging)
    implementation(libs.retrofit.core)
    implementation(libs.converter.gson)

    //Json parsers
    implementation(libs.gson)
    implementation(libs.moshi.kotlin)
}