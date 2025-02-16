plugins {
    alias(libs.plugins.asta.android.library)
    alias(libs.plugins.protobuf)
    alias(libs.plugins.asta.android.hilt)
    id("kotlin-parcelize")
}

android {
    namespace = "fit.asta.health.core.datastore"

    defaultConfig {
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
}

protobuf {
    protoc {
        artifact = libs.protobuf.protoc.get().toString()
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

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    testImplementation(libs.junit.jupiter)
    androidTestImplementation(libs.androidx.test.espresso.core)

    //Datastore
    implementation(libs.androidx.datastore.core)
    implementation(libs.protobuf.kotlin.lite)
}