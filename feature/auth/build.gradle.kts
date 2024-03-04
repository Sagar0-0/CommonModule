plugins {
    alias(libs.plugins.asta.android.library)
    alias(libs.plugins.asta.android.hilt)
    alias(libs.plugins.asta.android.library.compose)
    alias(libs.plugins.asta.android.test.feature)
}

android {
    namespace = "fit.asta.health.feature.auth"
}

dependencies {

    implementation(project(":resources:drawables"))
    implementation(project(":resources:strings"))

    implementation(project(":libs:media"))
    implementation(project(":libs:ccp"))

    implementation(project(":core:datastore"))
    implementation(project(":core:common"))
    implementation(project(":core:designsystem"))
    implementation(project(":core:test"))

    implementation(project(":data:auth"))
    implementation(project(":data:onboarding"))
    implementation(project(":data:profile"))

    //Firebase authentication
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.auth)
    implementation(libs.gms.play.services.auth)
    implementation(libs.gms.play.services.auth.api.phone)
    implementation(libs.firebase.ui.auth)

    //Firebase for notifications
    implementation(libs.firebase.messaging)

    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.hilt.navigation.compose)

    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.lifecycle.viewModelCompose)
    implementation(libs.androidx.lifecycle.runtimeCompose)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.navigation.runtime.ktx)
    implementation(libs.androidx.foundation.android)

    //Media 3
    implementation(libs.androidx.media3.exoplayer)
    implementation(libs.androidx.media3.exoplayer.dash)
    implementation(libs.androidx.media3.ui)
    implementation(libs.androidx.media3.common)
    implementation(libs.androidx.media3.session)
    implementation(project(mapOf("path" to ":libs:otpfield")))
    implementation(libs.androidx.ui.tooling.preview.android)
}