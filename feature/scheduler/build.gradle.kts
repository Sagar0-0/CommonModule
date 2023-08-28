plugins {
    alias(libs.plugins.asta.android.library)
    alias(libs.plugins.asta.android.hilt)
    alias(libs.plugins.asta.android.library.compose)
    alias(libs.plugins.asta.android.test.feature)
    id("kotlin-parcelize")
}

android {
    namespace = "fit.asta.health.feature.scheduler"
    defaultConfig {
        consumerProguardFiles("consumer-rules.pro")
        manifestPlaceholders["redirectSchemeName"] = "spotify-sdk"
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit4)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.test.espresso.core)
    //Jetpack Compose - Material theme components
    implementation(libs.androidx.material3)
    implementation(libs.androidx.material3.window.size)
    implementation(libs.androidx.material.icons.extended.android)
    implementation(libs.compose.theme.adapter)

    implementation(project(":data:scheduler"))
    implementation(project(":data:auth"))
    implementation(project(":data:spotify"))
    implementation(project(":core:common"))
    implementation(project(":core:datastore"))
    implementation(project(":core:designsystem"))
    implementation(project(":resources:strings"))
    implementation(project(":resources:drawables"))

    api(project(":libs:spotify-app-remote"))
    //Spotify Auth
    implementation(libs.auth)

    //Number Picker
    implementation(libs.numberpicker)

    //Number Picker
    implementation(libs.number.picker)
    //Swipe
    implementation(libs.swipe)

    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.hilt.navigation.compose)
    implementation(libs.androidx.material.icons.extended.android)

    implementation(libs.coil.kt.compose)

    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.lifecycle.viewModelCompose)
    implementation(libs.androidx.lifecycle.runtimeCompose)
    implementation(libs.hilt.android)

    kapt(libs.hilt.compiler)
    kapt(libs.hilt.ext.compiler)
//    implementation(libs.androidx.hilt.common)
    implementation(libs.androidx.hilt.navigation.compose)
    implementation(libs.hilt.ext.work)
    //Work Manager
    implementation(libs.androidx.work.ktx)

    implementation(libs.gson)

    implementation(libs.androidx.media3.exoplayer)
    implementation(libs.androidx.media3.exoplayer.dash)
    implementation(libs.androidx.media3.ui)
    implementation(libs.androidx.media3.common)
    implementation(libs.androidx.media3.session)
    //Image - Glide library
    implementation(libs.glide)

    implementation(libs.androidx.lifecycle.extensions)
}