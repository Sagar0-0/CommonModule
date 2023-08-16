import com.google.firebase.crashlytics.buildtools.gradle.CrashlyticsExtension
import com.google.firebase.perf.plugin.FirebasePerfExtension
import java.util.Properties

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.devtools.ksp")
    id("dagger.hilt.android.plugin")
    id("com.google.gms.google-services")
    id("com.google.firebase.crashlytics")
    id("com.google.firebase.firebase-perf")
    id("kotlin-parcelize")
    id("com.google.android.libraries.mapsplatform.secrets-gradle-plugin")
    id("com.google.protobuf")
}

val secretProps = Properties().apply {
    load(rootProject.file("secrets.defaults.properties").inputStream())
}

android {

    signingConfigs {
        create("release") {
            storeFile = file("..\\Keys\\AstaKey")
            storePassword = "ma123$%^SH"
            keyAlias = "AstaKey"
            keyPassword = "ra123$%^NI"
        }
    }

    namespace = "fit.asta.health"
    compileSdk = 34

    defaultConfig {
        applicationId = "fit.asta.health"
        minSdk = 24
        targetSdk = 34
        versionCode = 12
        versionName = "0.12"

        vectorDrawables.useSupportLibrary = true
        signingConfig = signingConfigs.getByName("release")

        //archivesBaseName = "$applicationId-$versionName"
        manifestPlaceholders["redirectSchemeName"] = "spotify-sdk"
        manifestPlaceholders["redirectHostName"] = "auth"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        javaCompileOptions {
            annotationProcessorOptions {
                arguments["room.schemaLocation"] = "$projectDir/schemas"
            }
        }

        buildConfigField("String", "BASE_URL", "\"${project.findProperty("base_url") ?: ""}\"")
        buildConfigField(
            "String",
            "BASE_IMAGE_URL",
            "\"${project.findProperty("base_image_url") ?: ""}\""
        )
        buildConfigField(
            "String",
            "BASE_VIDEO_URL",
            "\"${project.findProperty("base_video_url") ?: ""}\""
        )
    }

    flavorDimensions += "endpoints"

    productFlavors {
        create("dev") {
            dimension = "endpoints"
            resourceConfigurations += listOf("en", "xxhdpi")
            buildConfigField(
                "String",
                "BASE_URL",
                "\"${project.findProperty("dev_base_url") ?: ""}\""
            )
            buildConfigField(
                "String",
                "BASE_IMAGE_URL",
                "\"${project.findProperty("dev_image_url") ?: ""}\""
            )
            buildConfigField(
                "String",
                "BASE_VIDEO_URL",
                "\"${project.findProperty("dev_video_url") ?: ""}\""
            )
        }

        create("prod") {
            dimension = "endpoints"
            buildConfigField(
                "String",
                "BASE_URL",
                "\"${project.findProperty("prod_base_url") ?: ""}\""
            )
            buildConfigField(
                "String",
                "BASE_IMAGE_URL",
                "\"${project.findProperty("prod_image_url") ?: ""}\""
            )
            buildConfigField(
                "String",
                "BASE_VIDEO_URL",
                "\"${project.findProperty("prod_video_url") ?: ""}\""
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
        isCoreLibraryDesugaringEnabled = true
    }

    kotlinOptions {
        jvmTarget = "17"
    }

    buildTypes {
        getByName("debug") {
            applicationIdSuffix = ".dev"
            isMinifyEnabled = false
            //multiDexEnabled = true
            aaptOptions.cruncherEnabled = false
            configure<FirebasePerfExtension> {
                setInstrumentationEnabled(false)
            }
            configure<CrashlyticsExtension> {
                mappingFileUploadEnabled = false
                nativeSymbolUploadEnabled = false
            }
            resValue("string", "MAPS_API_KEY", secretProps["MAPS_API_KEY"].toString())
            manifestPlaceholders["crashlyticsCollectionEnabled"] = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        getByName("release") {
            isMinifyEnabled = true
            isShrinkResources = true
            resValue("string", "MAPS_API_KEY", secretProps["MAPS_API_KEY"].toString())
            manifestPlaceholders["crashlyticsCollectionEnabled"] = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    // Enables data binding.
    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }

    testOptions {
        unitTests {
            isIncludeAndroidResources = true
        }
    }
}

protobuf {
    protoc {
        artifact = "com.google.protobuf:protoc:3.23.4"
    }
    generateProtoTasks {
        all().forEach { task ->
            task.builtins {
                register("java") {
                    option("lite")
                }
                register("kotlin") {
                    option("lite")
                }
            }
        }
    }
}

/*androidComponents {
    beforeEvaluate { variant ->
        val variantName = variant.name
        android.sourceSets.register(variantName) {
            java.srcDir(buildDir.resolve("generated/source/proto/${variantName}/java"))
            kotlin.srcDir(buildDir.resolve("generated/source/proto/${variantName}/kotlin"))
        }
    }
}*/

dependencies {

    implementation(project(path = ":chartLibrary"))
    // Spotify App remote Dependency
    implementation(fileTree(mapOf("include" to listOf("*.jar", "*.aar"), "dir" to "libs")))

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
    implementation(libs.androidx.dataStore.core)
    implementation(libs.protobuf.kotlin.lite)
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

    //Firebase analytics
    implementation(libs.kotlinx.coroutines.play.services)
    implementation(libs.firebase.analytics.ktx)
    implementation(libs.firebase.crashlytics.ktx)
    implementation(libs.firebase.perf.ktx)
    implementation(libs.firebase.config.ktx)

    //Firebase storage for media files
    implementation(libs.firebase.storage.ktx)

    //Firebase fire-store for nosql
    implementation(libs.firebase.firestore.ktx)

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
    ksp(libs.hilt.compiler)
    ksp(libs.hilt.ext.compiler)
    implementation(libs.androidx.hilt.common)
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
    implementation(libs.exoplayer)
    implementation(libs.extension.cast)
    implementation(libs.exoplayer.core)
    implementation(libs.exoplayer.dash)
    implementation(libs.exoplayer.ui)
    api(libs.extension.mediasession)

    implementation(libs.androidx.media3.exoplayer)
    implementation(libs.androidx.media3.exoplayer.dash)
    implementation(libs.androidx.media3.ui)
    implementation(libs.androidx.media3.common)
    implementation(libs.androidx.media3.session)

    //Maps
    implementation(libs.maps.compose)
    implementation(libs.play.services.maps)
    implementation(libs.play.services.location)
    implementation(libs.places)

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

    //Spotify Auth
    implementation(libs.auth)

    //Stepper
    implementation(libs.compose.stepper)

    //Swipe
    implementation(libs.swipe)

    //Transformation view
    implementation(libs.transformationlayout)

    //Work Manager
    implementation(libs.androidx.work.runtime.ktx)

    //Moshi
    implementation(libs.moshi.kotlin)

    //referral
    implementation(libs.installreferrer)

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
    // Photo Picker
    implementation("com.google.modernstorage:modernstorage-photopicker:1.0.0-alpha06")

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

    androidTestImplementation(libs.androidx.ui.test.junit4.android)
    androidTestImplementation(libs.androidx.room.testing)
    androidTestImplementation(libs.androidx.test.espresso.core)
    androidTestImplementation(libs.androidx.junit)

}