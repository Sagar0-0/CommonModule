plugins {
    alias(libs.plugins.asta.android.library)
    alias(libs.plugins.asta.android.hilt)
    alias(libs.plugins.asta.android.library.compose)
    alias(libs.plugins.asta.android.test.feature)
}

android {
    namespace = "fit.asta.health.feature.spotify"
    defaultConfig {
        consumerProguardFiles("consumer-rules.pro")
        manifestPlaceholders["redirectSchemeName"] = "spotify-sdk"
    }
}

dependencies {

    // Do not include local .aar or .jar dependencies here
    //implementation(fileTree(mapOf("include" to listOf("*.jar", "*.aar"), "dir" to "../../mobile/libs")))
    api(project(":spotify-app-remote"))

    implementation(project(":resources:strings"))
    implementation(project(":resources:drawables"))

    implementation(project(":core:common"))
    implementation(project(":core:designsystem"))

    implementation(project(":data:spotify"))

    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.hilt.navigation.compose)
    implementation(libs.androidx.material.icons.extended.android)

    implementation(libs.coil.kt.compose)

    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.lifecycle.viewModelCompose)
    implementation(libs.androidx.lifecycle.runtimeCompose)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.navigation.runtime.ktx)
    implementation(libs.androidx.foundation.android)
    implementation(libs.androidx.material3)

    //Spotify Auth
    implementation(libs.auth)
}