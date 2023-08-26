plugins {
    alias(libs.plugins.asta.android.library)
    alias(libs.plugins.asta.android.test.feature)
}

android {
    namespace = "fit.asta.health.core.test"
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)

    implementation(libs.mockk.android)
    implementation(libs.kotlinx.coroutines.test)
    implementation(libs.junit.jupiter)
}