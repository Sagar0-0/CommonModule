plugins {
    id("asta.android.library")
    id("asta.android.library.compose")
}

android {
    namespace = "fit.asta.chart"

    defaultConfig {
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(enforcedPlatform(libs.kotlin.bom))
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(enforcedPlatform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.material3)

    testImplementation(libs.junit.jupiter)
    androidTestImplementation(libs.androidx.test.espresso.core)
    androidTestImplementation(enforcedPlatform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test)
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
}