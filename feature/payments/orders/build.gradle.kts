plugins {
    alias(libs.plugins.asta.android.library)
    alias(libs.plugins.asta.android.hilt)
    alias(libs.plugins.asta.android.library.compose)
    alias(libs.plugins.asta.android.test.feature)
}

android {
    namespace = "fit.asta.health.feature.orders"
}

dependencies {
    implementation(project(":resources:strings"))
    implementation(project(":resources:drawables"))

    implementation(project(":core:common"))
    implementation(project(":core:designsystem"))
    implementation(project(":core:test"))

    implementation(project(":data:payments:orders"))
    implementation(project(":data:auth"))

    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.hilt.navigation.compose)

    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.lifecycle.viewModelCompose)
    implementation(libs.androidx.lifecycle.runtimeCompose)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.navigation.runtime.ktx)
    implementation(libs.androidx.foundation.android)
    implementation(libs.accompanist.coil)

    implementation(libs.moshi.kotlin)
    implementation(libs.androidx.ui.tooling.preview.android)
}