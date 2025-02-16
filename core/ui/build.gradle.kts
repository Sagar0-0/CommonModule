plugins {
    alias(libs.plugins.asta.android.library)
    alias(libs.plugins.asta.android.library.compose)
    id("kotlin-parcelize")
}

android {
    namespace = "fit.asta.health.ui"

    defaultConfig {
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
}

dependencies {

    implementation(project(":resources:strings"))
    implementation(project(":resources:drawables"))
    implementation(project(":core:common"))
    implementation(project(":core:designsystem"))

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.ui.unit.android)
    implementation(libs.androidx.documentfile)

    testImplementation(libs.junit.jupiter)
    androidTestImplementation(libs.androidx.test.espresso.core)

    //Jetpack Compose
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.testManifest)
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
    implementation(libs.compose.theme.adapter)

    //Jetpack Compose - Pager
    implementation(libs.androidx.paging.runtime.ktx)
    implementation(libs.androidx.paging.compose)

    //Jetpack Compose - Paging
    implementation(libs.accompanist.pager)
    implementation(libs.accompanist.pager.indicators)
    implementation(libs.accompanist.coil)

    //Jetpack Compose - Navigation
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
    implementation(libs.androidx.navigation.compose)

    //Accompanist-BottomSheet Nav
    implementation(libs.accompanist.insets)
    implementation(libs.accompanist.drawablepainter)
    implementation(libs.accompanist.navigation.animation)
    implementation(libs.accompanist.systemuicontroller)
    implementation(libs.accompanist.flowlayout)

    //Image - Coil library
    implementation(libs.coil.kt.compose)
    implementation(libs.coil.gif)

    implementation(libs.balloon)


    //TODO-------------------Low standard libraries - consider removing later---------------------//
    //Rating-Bar
    implementation("com.github.a914-gowtham:compose-ratingbar:1.2.4")
    implementation("com.github.SmartToolFactory:Compose-RatingBar:2.1.1")
    //TODO-------------------Low standard libraries - consider removing later---------------------//
}