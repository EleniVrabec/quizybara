plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    kotlin("plugin.serialization") version "2.0.21"
}

android {
    namespace = "com.elvr.quizybara"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.elvr.quizybara"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.room.compiler)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    val nav_version = "2.8.6"
    implementation ("androidx.compose.ui:ui:1.4.0")
    implementation("androidx.navigation:navigation-compose:$nav_version")

    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.7.3")
    val material3_version = "1.3.1"
    implementation ("androidx.compose.material3:material3:$material3_version")
    implementation ("androidx.compose.material:material-icons-extended:1.5.1")

    // eftermontering


    implementation (libs.retrofit)


// GSON


    implementation (libs.converter.gson)


// coroutine


    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.5.2")


    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.5.2")

    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.8.5")

    implementation("io.coil-kt:coil-compose:2.3.0")
    implementation("io.coil-kt:coil-compose:2.3.0") // Coil for image loading
    implementation("io.coil-kt:coil-svg:2.3.0") // ✅ SVG Support for Coil
    implementation("com.airbnb.android:lottie-compose:6.1.0")
  //  implementation("nl.dionsegijn:konfetti-compose:2.0.2")
    implementation("nl.dionsegijn:konfetti-compose:2.0.5")
    implementation("androidx.datastore:datastore-preferences:1.1.3")

}

// ✅ Kotlin DSL syntax — put this after dependencies { }
configurations.all {
    exclude(group = "com.intellij", module = "annotations")
}