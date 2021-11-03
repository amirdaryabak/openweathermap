// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    repositories {
        google()
        mavenCentral()
        jcenter()
    }
    dependencies {
        classpath("com.android.tools.build:gradle:7.0.3")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlinVersion}")
        classpath("com.google.dagger:hilt-android-gradle-plugin:${Dependencies.hiltVersion}")
        classpath("com.google.gms:google-services:4.3.5")
        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:2.3.4")
    }
}

task("clean") {
    delete(rootProject.buildDir)
}