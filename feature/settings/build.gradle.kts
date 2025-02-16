plugins {
    alias(libs.plugins.asta.android.library)
    alias(libs.plugins.asta.android.hilt)
    alias(libs.plugins.asta.android.library.compose)
    alias(libs.plugins.asta.android.test.feature)
}

android {
    namespace = "fit.asta.health.feature.settings"
}

dependencies {

    implementation(project(":resources:strings"))

    implementation(project(":core:common"))
    implementation(project(":core:designsystem"))
    implementation(project(":core:datastore"))
    implementation(project(":core:ui"))

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

    //Firebase for notifications
    implementation(libs.firebase.messaging)

    // For instrumented tests.
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    debugImplementation(libs.androidx.compose.ui.testManifest)
    androidTestImplementation(libs.hilt.android.testing)
    kaptAndroidTest(libs.hilt.compiler)
}