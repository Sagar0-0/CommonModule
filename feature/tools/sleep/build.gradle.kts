@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.asta.android.library)
    alias(libs.plugins.asta.android.hilt)
    alias(libs.plugins.asta.android.library.compose)
    alias(libs.plugins.asta.android.test.feature)
    id("kotlin-parcelize")
}

android {
    namespace = "fit.asta.health.feature.sleep"
    packaging {
        resources {
            excludes += "META-INF/LICENSE.md"
            excludes += "META-INF/LICENSE-notice.md"
            excludes += "META-INF/DEPENDENCIES"
            excludes += "META-INF/LICENSE"
            excludes += "META-INF/LICENSE.txt"
            excludes += "META-INF/license.txt"
            excludes += "META-INF/NOTICE"
            excludes += "META-INF/NOTICE.txt"
            excludes += "META-INF/notice.txt"
            excludes += "META-INF/ASL2.0"
            excludes += "META-INF/*.kotlin_module"
        }
    }
}

dependencies {
    implementation(project(":data:tools:sleep"))
    implementation(project(":data:auth"))
    implementation(project(":core:common"))
    implementation(project(":core:test"))
    implementation(project(":core:datastore"))
    implementation(project(":core:network"))
    implementation(project(":core:designsystem"))
    implementation(project(":resources:strings"))
    implementation(project(":resources:drawables"))


    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    //Jetpack Compose - Material theme components
    implementation(libs.androidx.material3)
    implementation(libs.androidx.material3.window.size)
    implementation(libs.androidx.material.icons.extended.android)
    implementation(libs.compose.theme.adapter)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.hilt.navigation.compose)
    implementation(libs.androidx.material.icons.extended.android)

    implementation(libs.coil.kt.compose)

    // Color picker
    implementation(libs.smarttoolfactory.color.picker.bundle)


    // Colorful Customizable Sliders
    implementation("com.github.SmartToolFactory:Compose-Colorful-Sliders:1.2.0")
    // Color picker
    implementation("com.github.SmartToolFactory:Compose-Color-Picker-Bundle:1.0.0")
    // Gestures
    implementation("com.github.SmartToolFactory:Compose-Extended-Gestures:3.0.0")
    // Animated List
    implementation("com.github.SmartToolFactory:Compose-AnimatedList:0.5.1")
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.lifecycle.viewModelCompose)
    implementation(libs.androidx.lifecycle.runtimeCompose)
    implementation(libs.androidx.health.connect.client)
    implementation(libs.hilt.android)
    implementation(libs.androidx.hilt.navigation.compose)
    implementation(libs.hilt.ext.work)
    //Work Manager
    implementation(libs.androidx.work.ktx)

    implementation(libs.gson)

    implementation(libs.androidx.lifecycle.extensions)
    implementation(libs.androidx.navigation.testing)

    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    debugImplementation(libs.androidx.compose.ui.testManifest)

    // For instrumented tests.
    androidTestImplementation(libs.hilt.android.testing)
    androidTestImplementation("androidx.test:runner:1.5.2")
}