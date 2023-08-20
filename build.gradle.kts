buildscript {
    repositories {
        google()
        mavenCentral()
        // Android Build Server
        maven { url = uri("../asta-prebuilts/m2repository") }
    }

    dependencies {
        classpath(libs.google.oss.licenses.plugin)
    }
}

// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.jvm) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.firebase.crashlytics) apply false
    alias(libs.plugins.firebase.perf) apply false
    alias(libs.plugins.gms) apply false
    alias(libs.plugins.hilt) apply false
    alias(libs.plugins.ksp) apply false
    alias(libs.plugins.secrets) apply false
    alias(libs.plugins.protobuf) apply false
    alias(libs.plugins.org.jetbrains.kotlin.android) apply false

    // id("org.jetbrains.kotlin.android") version "$kotlin_ver" apply false
    // id("com.faire.gradle.analyze") version "1.0.9" apply false //for analyzing the gradle builds
    // id("com.osacky.doctor") version "0.8.1"
}