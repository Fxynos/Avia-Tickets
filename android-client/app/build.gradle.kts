plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.kotlin.kapt)
    alias(libs.plugins.hilt.android)
    alias(libs.plugins.navigation.safeargs)
}

android {
    namespace = "com.vl.aviatickets"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.vl.aviatickets"
        minSdk = 28
        targetSdk = 34
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

    buildFeatures {
        viewBinding = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    /* Modules */
    implementation(project(":data"))
    implementation(project(":domain"))

    /* Hilt */
    kapt(libs.hilt.android.compiler)
    implementation(libs.hilt.android)

    /* Navigation */
    implementation(libs.navigation.fragment)
    implementation(libs.navigation.ui)

    /* Coil (Asynchronous images loading on UI-side) */
    implementation(libs.coil.kt)

    /* Shimmer */
    implementation(libs.shimmer)

    /* Adapter Delegates */
    implementation(libs.adapter.delegates.dsl)
    implementation(libs.adapter.delegates.viewbinding)

    /* Android */
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
}