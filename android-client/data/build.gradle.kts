import com.android.build.api.dsl.BuildType
import java.util.Properties

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.kotlin.android)
}

android {
    namespace = "com.vl.aviatickets.data"
    compileSdk = 34

    defaultConfig {
        minSdk = 28

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        fun BuildType.applySharedSettings() {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt",),
                "proguard-rules.pro"
            )
            buildConfigField("String", "API_BASE_URL", Properties().apply {
                load(file("../dev.properties").inputStream())
            }.getProperty("api_base_url"))
        }

        debug { applySharedSettings() }
        release { applySharedSettings() }
    }

    buildFeatures {
        buildConfig = true
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
    implementation(project(":domain"))

    /* DataStore */
    implementation(libs.datastore.preferences)

    /* Retrofit */
    implementation(libs.retrofit.retrofit)
    implementation(libs.retrofit.converter.gson)
}