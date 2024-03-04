plugins {
    alias(libs.plugins.asta.android.library)
    alias(libs.plugins.asta.android.hilt)
    alias(libs.plugins.asta.android.test.data)
    id("kotlin-parcelize")
}

android {
    namespace = "fit.asta.health.data.auth"
}

dependencies {
    implementation(project(":resources:strings"))

    implementation(project(":core:common"))
    implementation(project(":core:datastore"))
    implementation(project(":core:network"))

    implementation(libs.gson)
    implementation(libs.retrofit.core)
    implementation(libs.converter.gson)

    //Firebase
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.auth)
    implementation(libs.gms.play.services.auth)
    implementation(libs.gms.play.services.auth.api.phone)
    implementation(libs.firebase.ui.auth)
    implementation(libs.firebase.firestore)
    implementation(libs.firebase.messaging)
    implementation(libs.firebase.storage)
}