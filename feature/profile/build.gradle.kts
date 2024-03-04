plugins {
    alias(libs.plugins.asta.android.library)
    alias(libs.plugins.asta.android.hilt)
    alias(libs.plugins.asta.android.library.compose)
    alias(libs.plugins.asta.android.test.feature)
}

android {
    namespace = "fit.asta.health.feature.profile"
    defaultConfig {
        manifestPlaceholders["redirectSchemeName"] = "spotify-sdk"
        manifestPlaceholders["redirectHostName"] = "auth"
    }
}

dependencies {

    implementation(project(":resources:strings"))
    implementation(project(":resources:drawables"))

    implementation(project(":libs:otpfield"))
    implementation(project(":libs:ccp"))

    implementation(project(":core:common"))
    implementation(project(":core:designsystem"))
    implementation(project(":core:network"))
    implementation(project(":core:test"))
    implementation(project(":core:datastore"))

    implementation(project(":data:profile"))
    implementation(project(":data:auth"))

    implementation(project(":feature:auth"))

    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.hilt.navigation.compose)

    implementation(libs.coil.kt.compose)

    //Firebase authentication
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.auth)
    implementation(libs.gms.play.services.auth)
    implementation(libs.gms.play.services.auth.api.phone)
    implementation(libs.firebase.ui.auth)

    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.lifecycle.viewModelCompose)
    implementation(libs.androidx.lifecycle.runtimeCompose)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.navigation.runtime.ktx)
    implementation(libs.androidx.foundation.android)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.constraintlayout.compose)
    implementation(libs.androidx.compose.ui)

    implementation(libs.calendar)
    implementation(libs.clock)
    implementation(libs.accompanist.flowlayout)

    //Scheduler
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.compose.theme.adapter)
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

    implementation(libs.coil.kt.compose)

    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.lifecycle.viewModelCompose)
    implementation(libs.androidx.lifecycle.runtimeCompose)

    //Hilt Navigation
    implementation(libs.androidx.hilt.navigation.compose)

    //Work Manager
    implementation(libs.hilt.ext.work)
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

    implementation("com.github.SmartToolFactory:Compose-Cropper:0.4.0")
}