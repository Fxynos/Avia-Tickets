plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.jetbrains.kotlin.android) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.kotlin.kapt) apply false
    alias(libs.plugins.hilt.android) apply false
    alias(libs.plugins.navigation.safeargs) apply false
}

buildscript {
    repositories {
        google()
    }
    dependencies {
        classpath(libs.navigation.safeargs)
    }
}