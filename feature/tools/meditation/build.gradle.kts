@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.asta.android.library)
    alias(libs.plugins.asta.android.hilt)
    alias(libs.plugins.asta.android.library.compose)
    alias(libs.plugins.asta.android.test.feature)
    id("kotlin-parcelize")
}

android {
    namespace = "fit.asta.health.feature.meditation"
}

dependencies {

    implementation(project(":libs:media"))
    implementation(project(":data:tools:meditation"))
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
    implementation(libs.compose.theme.adapter)

    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.hilt.navigation.compose)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.lifecycle.viewModelCompose)
    implementation(libs.androidx.lifecycle.runtimeCompose)

    //Hilt Navigation
    implementation(libs.androidx.hilt.navigation.compose)

    implementation(libs.coil.kt.compose)

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