plugins {
    id("asta.android.application")
    id("asta.android.application.compose")
    id("asta.android.application.flavors")
    id("asta.android.application.jacoco")
    id("jacoco")
    id("asta.android.hilt")
    id("com.google.devtools.ksp")
    id("kotlin-parcelize")
    id("asta.android.application.firebase")
}

android {

    namespace = "fit.asta.health"

    buildFeatures {
        buildConfig = true
    }

    signingConfigs {
        create("release") {
            storeFile = file("..\\Keys\\AstaKey")
            storePassword = "ma123$%^SH"
            keyAlias = "AstaKey"
            keyPassword = "ra123$%^NI"
        }
    }

    /* TODO - Need to test
    signingConfigs {
        if (rootProject.file("signing-debug.properties").exists()) {
            val signingDebug = Properties()
            signingDebug.load(java.io.FileInputStream(rootProject.file("signing-debug.properties")))
            getByName("debug") {
                storeFile = rootProject.file(signingDebug.getProperty("storeFile"))
                storePassword = signingDebug.getProperty("storePassword")
                keyAlias = signingDebug.getProperty("keyAlias")
                keyPassword = signingDebug.getProperty("keyPassword")
            }
        }
        if (rootProject.file("signing-release.properties").exists()) {
            val signingRelease = Properties()
            signingRelease.load(java.io.FileInputStream(rootProject.file("signing-release.properties")))
            create("release") {
                storeFile =  rootProject.file(signingRelease.getProperty("storeFile"))
                storePassword = signingRelease.getProperty("storePassword")
                keyAlias = signingRelease.getProperty("keyAlias")
                keyPassword = signingRelease.getProperty("keyPassword")
            }
        }
    }*/

    defaultConfig {
        applicationId = "fit.asta.health"
        versionCode = 19
        versionName = "0.1.9" // X.Y.Z; X = Major, Y = minor, Z = Patch level
        vectorDrawables.useSupportLibrary = true

        base.archivesName.set("$applicationId-$versionName")
        manifestPlaceholders["redirectSchemeName"] = "spotify-sdk"
        manifestPlaceholders["redirectHostName"] = "auth"
        signingConfig = signingConfigs.getByName("release")
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        debug {
            //multiDexEnabled = true
            isDebuggable = true
            aaptOptions.cruncherEnabled = false
            //signingConfig = signingConfigs.getByName("debug")
            //applicationIdSuffix = AstaBuildType.DEBUG.applicationIdSuffix
        }
        release {
            isDebuggable = false
            isMinifyEnabled = true
            isShrinkResources = true
            isJniDebuggable = false
            isRenderscriptDebuggable = false

            //signingConfig = signingConfigs.getByName("release")
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    packaging {
        resources {
            excludes.add("/META-INF/{AL2.0,LGPL2.1,LICENSE.md,LICENSE-notice.md}")
        }
    }

    /*TODO - Need to test
    bundle {
        language { enableSplit = true }
        density { enableSplit = true }
        abi { enableSplit = true }
    }*/

    testOptions {
        unitTests {
            this.all {
                it.useJUnitPlatform()
            }
            isIncludeAndroidResources = true
        }
    }
}

dependencies {

    // Spotify App remote Dependency
    implementation(fileTree(mapOf("include" to listOf("*.jar", "*.aar"), "dir" to "libs")))

    implementation(project(":core:datastore"))
    implementation(project(":core:designsystem"))
    implementation(project(":core:common"))
    implementation(project(":core:network"))

    implementation(project(":libs:chart"))
    implementation(project(":libs:audio"))
    implementation(project(":libs:video"))

    implementation(project(":data:auth"))
    implementation(project(":data:payment"))
    implementation(project(":data:address"))
    implementation(project(":data:payment"))
    implementation(project(":data:scheduler"))
    implementation(project(":data:testimonials"))

    implementation(project(":feature:address"))
    implementation(project(":feature:auth"))
    implementation(project(":feature:feedback"))
    implementation(project(":feature:onboarding"))
    implementation(project(":feature:orders"))
    implementation(project(":feature:payment"))
    implementation(project(":feature:profile"))
    implementation(project(":feature:referral"))
    implementation(project(":feature:settings"))
    implementation(project(":feature:subscription"))
    implementation(project(":feature:wallet"))
    implementation(project(":data:spotify"))
    implementation(project(":feature:spotify"))
    implementation(project(":feature:scheduler"))
    implementation(project(":feature:testimonials"))
    implementation(project(":core:test"))

    //De-sugaring
    coreLibraryDesugaring(libs.android.desugarJdkLibs)

    //multidex support
    //implementation("androidx.multidex:multidex:2.0.1")

    implementation(libs.kotlin.stdlib)
    // Coroutines
    implementation(libs.kotlinx.coroutines.core)
    //Androidx
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.legacy.support.v4)
    implementation(libs.androidx.lifecycle.extensions)
    implementation(libs.androidx.lifecycle.livedata.ktx)
    implementation(libs.androidx.preference.ktx)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.palette.ktx)
    implementation(libs.androidx.window.manager)

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

    //Datastore
    implementation(libs.androidx.datastore.core)
    implementation(libs.androidx.datastore.preferences)

    //Jetpack Compose - Material theme components
    implementation(libs.androidx.material3)
    implementation(libs.androidx.material3.window.size)
    implementation(libs.androidx.material.icons.extended.android)
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
    implementation(libs.accompanist.navigation.material)
    implementation(libs.accompanist.insets)
    implementation(libs.accompanist.drawablepainter)
    implementation(libs.accompanist.navigation.animation)
    implementation(libs.accompanist.systemuicontroller)
    implementation(libs.accompanist.flowlayout)

    //In-app updates
    implementation(libs.app.update.ktx)

    //In-app reviews
    implementation(libs.review.ktx)

    //Firebase authentication
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.auth.ktx)
    implementation(libs.play.services.auth)
    implementation(libs.play.services.auth.api.phone)
    implementation(libs.firebase.ui.auth)

    //Firebase for notifications
    implementation(libs.firebase.messaging.ktx)
    implementation(libs.firebase.firestore.ktx)

    //Firebase analytics
    implementation(libs.kotlinx.coroutines.play.services)
    implementation(libs.firebase.analytics.ktx)
    implementation(libs.firebase.crashlytics.ktx)
    implementation(libs.firebase.perf.ktx)
    implementation(libs.firebase.config.ktx)

    //Firebase storage for media files
    implementation(libs.firebase.storage.ktx)


    //for animations
    implementation(libs.androidx.constraintlayout.compose)
    implementation(libs.lottie.compose)

    //splash
    implementation(libs.androidx.core.splashscreen)

    //Image - Glide library
    implementation(libs.glide)
    implementation(libs.fluid.slider)

    //Image - Coil library
    implementation(libs.coil.kt.compose)
    implementation(libs.coil.gif)

    //Image - Circular view
    implementation(libs.circularimageview)

    // Room Library
    implementation(libs.room.runtime)
    ksp(libs.room.compiler)
    implementation(libs.room.ktx)
    androidTestImplementation(libs.androidx.room.testing)

    //Dagger Hilt
    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)
    kapt(libs.hilt.ext.compiler)
//    implementation(libs.androidx.hilt.common)
    implementation(libs.androidx.hilt.navigation.compose)
    implementation(libs.hilt.ext.work)

    // Retrofit and OkHttp
    implementation(libs.okhttp)
    implementation(libs.okhttp.logging)
    implementation(libs.retrofit.core)
    implementation(libs.converter.gson)

    //Json parser
    implementation(libs.gson)

    //Exo player
//    implementation(libs.exoplayer)
//    implementation(libs.extension.cast)
//    implementation(libs.exoplayer.core)
//    implementation(libs.exoplayer.dash)
//    implementation(libs.exoplayer.ui)
//    api(libs.extension.mediasession)

    implementation(libs.androidx.media3.exoplayer)
    implementation(libs.androidx.media3.exoplayer.dash)
    implementation(libs.androidx.media3.ui)
    implementation(libs.androidx.media3.common)
    implementation(libs.androidx.media3.session)

    //Billing
    implementation(libs.billing)
    implementation(libs.billing.ktx)

    //Payment - Razorpay
    implementation(libs.checkout)

    //Calendar
    implementation(libs.core)
    implementation(libs.calendar)

    //Clock
    implementation(libs.clock)

    //FlexBox
    implementation(libs.flexbox)

    //Number Picker
    implementation(libs.numberpicker)

    //Number Picker
    implementation(libs.number.picker)

    //Swipe
    implementation(libs.swipe)

    //Transformation view
    implementation(libs.transformationlayout)

    //Work Manager
    implementation(libs.androidx.work.ktx)

    //Moshi
    implementation(libs.moshi.kotlin)

    //referral
    implementation(libs.installreferrer)

    // Photo Picker
    implementation(libs.modernstorage.photopicker)

    //Referral
    implementation(libs.installreferrer)

    //TODO-------------------Low standard libraries - consider removing later---------------------//
    //Stepper
    implementation("com.github.maryamrzdh:compose-stepper:1.0.0-beta01")

    //Rating-Bar
    implementation("com.github.a914-gowtham:compose-ratingbar:1.2.4")
    implementation("com.github.SmartToolFactory:Compose-RatingBar:2.1.1")

    //Recyclerview swipe decorator
    implementation("com.github.xabaras:RecyclerViewSwipeDecorator:1.4")

    //Swipe action view for snooze and stop
    implementation("com.github.Tunous:SwipeActionView:1.4.0")

    //Tone picking
    implementation("xyz.aprildown:UltimateRingtonePicker:3.1.0")

    //Image Picker
    implementation("com.github.Drjacky:ImagePicker:2.3.22")

    //Image Cropper Dependencies
    // Colorful Customizable Sliders
    implementation("com.github.SmartToolFactory:Compose-Colorful-Sliders:1.2.0")
    // Color picker
    implementation("com.github.SmartToolFactory:Compose-Color-Picker-Bundle:1.0.0")
    // Gestures
    implementation("com.github.SmartToolFactory:Compose-Extended-Gestures:3.0.0")
    // Animated List
    implementation("com.github.SmartToolFactory:Compose-AnimatedList:0.5.1")
    //TODO-------------------Low standard libraries - consider removing later---------------------//

    //test
    testImplementation(libs.junit4)
    testImplementation(libs.junit.jupiter)
    testImplementation(libs.junit.vintage.engine)
    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.androidx.core.testing)
    testImplementation(libs.truth)
    testImplementation(libs.androidx.paging.common.ktx)
    testImplementation(libs.mockk.android)
    testImplementation(libs.turbine)
    testImplementation(libs.mockwebserver)

    androidTestImplementation(libs.androidx.ui.test.junit4.android)
    androidTestImplementation(libs.androidx.room.testing)
    androidTestImplementation(libs.androidx.test.espresso.core)
    androidTestImplementation(libs.androidx.junit)
}