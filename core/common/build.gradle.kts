plugins {
    alias(libs.plugins.asta.android.library)
    alias(libs.plugins.asta.android.hilt)
    alias(libs.plugins.asta.android.library.compose)
    id("com.google.android.libraries.mapsplatform.secrets-gradle-plugin")
    id("kotlin-parcelize")
}

secrets {
    defaultPropertiesFileName = "secrets.properties"
}

android {

    buildFeatures {
        buildConfig = true
    }

    namespace = "fit.asta.health.core.common"
    defaultConfig {
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }
}

dependencies {

    implementation(project(":resources:strings"))
    implementation(project(":resources:drawables"))

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.ui.android)
    implementation(libs.androidx.navigation.common.ktx)
    implementation(libs.androidx.navigation.runtime.ktx)

    //Jetpack Compose
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.ui.tooling)
    implementation(libs.androidx.compose.ui.util)
    implementation(libs.androidx.compose.animation)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.lifecycle.viewModelCompose)
    implementation(libs.androidx.lifecycle.runtimeCompose)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.constraintlayout.compose)
    implementation(libs.androidx.compose.foundation)

    //Jetpack Compose - Material theme components
    implementation(libs.androidx.material3)
    implementation(libs.androidx.material3.window.size)
    implementation(libs.androidx.material.icons.extended.android)
    implementation(libs.compose.theme.adapter)

    //Image - Glide library
    implementation(libs.glide)
    implementation(libs.fluid.slider)

    //In-app reviews
    implementation(libs.review.ktx)

    // Photo Picker
    implementation(libs.modernstorage.photopicker)

    //TODO-------------------Low standard libraries - consider removing later---------------------//
    //Image Cropper Dependencies
    implementation("com.github.SmartToolFactory:Compose-RatingBar:2.1.1")
    // Colorful Customizable Sliders
    implementation("com.github.SmartToolFactory:Compose-Colorful-Sliders:1.2.0")
    // Color picker
    implementation("com.github.SmartToolFactory:Compose-Color-Picker-Bundle:1.0.0")
    // Gestures
    implementation("com.github.SmartToolFactory:Compose-Extended-Gestures:3.0.0")
    // Animated List
    implementation("com.github.SmartToolFactory:Compose-AnimatedList:0.5.1")
    //TODO-------------------Low standard libraries - consider removing later---------------------//
    implementation(libs.androidx.hilt.navigation.compose)
}