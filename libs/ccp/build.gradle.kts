plugins {
    alias(libs.plugins.asta.android.library)
    alias(libs.plugins.asta.android.hilt)
    alias(libs.plugins.asta.android.library.compose)
    alias(libs.plugins.asta.android.test.feature)
}

android {
    namespace = "fit.asta.ccp"

    packaging {
        resources {
            excludes += "META-INF/LICENSE.md"
            excludes += "META-INF/LICENSE-notice.md"
        }
    }

}

dependencies {

    implementation(project(":core:common"))
    implementation(project(":core:designsystem"))

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit4)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.test.espresso.core)

    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.lifecycle.viewModelCompose)
    implementation(libs.androidx.lifecycle.runtimeCompose)

    implementation(libs.androidx.navigation.runtime.ktx)
    implementation(libs.androidx.foundation.android)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.constraintlayout.compose)
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.material3.window.size)
    implementation(libs.androidx.material.icons.extended.android)
    implementation(libs.compose.theme.adapter)
    debugImplementation(libs.androidx.compose.ui.tooling)
    implementation(libs.androidx.compose.ui.graphics)

    implementation(libs.google.libphonenumber)

}