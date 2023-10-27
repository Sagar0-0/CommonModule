plugins {
    alias(libs.plugins.asta.android.library)
    alias(libs.plugins.asta.android.hilt)
    alias(libs.plugins.asta.android.library.compose)
    alias(libs.plugins.asta.android.test.feature)
}

android {
    namespace = "fit.asta.health.feature.feedback"
}

dependencies {

    androidTestImplementation("androidx.test:rules:1.5.0")
    androidTestImplementation("androidx.test:runner:1.6.0-alpha04")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.6.0-alpha01")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-intents:3.5.1")

    implementation(project(":resources:drawables"))
    implementation(project(":resources:strings"))

    implementation(project(":core:common"))
    implementation(project(":core:designsystem"))
    implementation(project(":core:test"))

    implementation(project(":data:feedback"))
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
    implementation(libs.androidx.material3)
    implementation(libs.androidx.documentfile)

    //Jetpack Compose - Pager
    implementation(libs.androidx.paging.runtime.ktx)
    implementation(libs.androidx.paging.compose)

    //Jetpack Compose - Paging
    implementation(libs.accompanist.pager)
    implementation(libs.accompanist.pager.indicators)
    implementation(libs.accompanist.coil)


    // Test rules and transitive dependencies:
    androidTestImplementation("androidx.compose.ui:ui-test-junit4:1.5.4")
    // Needed for createAndroidComposeRule, but not createComposeRule:
    debugImplementation("androidx.compose.ui:ui-test-manifest:1.5.4")

    // For instrumented tests.
    androidTestImplementation("com.google.dagger:hilt-android-testing:2.44")
    // ...with Kotlin.
    kaptAndroidTest("com.google.dagger:hilt-android-compiler:2.44")
}