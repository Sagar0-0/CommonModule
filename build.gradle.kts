buildscript {

    dependencies {
        classpath(libs.android.gradlePlugin)
        classpath(libs.kotlin.gradlePlugin)
        classpath(libs.hilt.android.gradle.plugin)
        classpath(libs.google.services)
        classpath(libs.firebase.crashlytics.gradlePlugin)
        classpath(libs.firebase.performance.gradle)
    }
}

// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.kotlin.jvm) apply false
    alias(libs.plugins.firebase.crashlytics) apply false
    alias(libs.plugins.firebase.perf) apply false
    alias(libs.plugins.gms) apply false
    alias(libs.plugins.hilt) apply false
    alias(libs.plugins.ksp) apply false
    alias(libs.plugins.secrets) apply false
    alias(libs.plugins.protobuf) apply false

    // id("org.jetbrains.kotlin.android") version "$kotlin_ver" apply false
    // id("com.faire.gradle.analyze") version "1.0.9" apply false //for analyzing the gradle builds
    // id("com.osacky.doctor") version "0.8.1"
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}