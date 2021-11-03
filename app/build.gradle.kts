plugins {
    id("com.android.application")
    id("dagger.hilt.android.plugin")
    kotlin("android")
    kotlin("kapt")
    id("androidx.navigation.safeargs.kotlin")
}

android {
    compileSdkVersion(Versions.compileSdkVersion)
    buildToolsVersion(Versions.buildToolsVersion)

    defaultConfig {
        applicationId = "ir.amirdaryabak.openweathermap"
        minSdkVersion(Versions.minSdkVersion)
        targetSdkVersion(Versions.targetSdkVersion)
        versionCode = Versions.versionCode
        versionName = Versions.versionName

    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            isShrinkResources = false
            proguardFiles(
                getDefaultProguardFile(
                    "proguard-android.txt"
                ),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    buildFeatures {
        viewBinding = true
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    implementation(Dependencies.App.kotlinStdLib)
    implementation(Dependencies.App.core)
    implementation(Dependencies.App.appCompat)
    implementation(Dependencies.App.material)
    implementation(Dependencies.App.constraintLayout)

    implementation(Dependencies.App.lifecycle)
    implementation(Dependencies.App.recyclerview)
    implementation(Dependencies.App.navigationFragment)
    implementation(Dependencies.App.navigationUi)
    implementation(Dependencies.App.hilt)
    kapt(Dependencies.App.hiltCompiler)
    implementation(Dependencies.App.hiltViewModel)
    implementation(Dependencies.App.hiltJetpackCompiler)
    implementation(Dependencies.App.viewModel)
    implementation(Dependencies.App.liveDataKtx)
    implementation(Dependencies.App.timber)
    implementation(Dependencies.App.gms)
    implementation(Dependencies.App.gmsLocation)
    implementation(Dependencies.App.gmsPlaces)
    implementation(Dependencies.App.gmsLibrariesPlaces)
    implementation(Dependencies.App.legacySupport)
    implementation(Dependencies.App.lifecycleExtensions)
    implementation(Dependencies.App.fragment)
    implementation(Dependencies.App.activityKTX)
    implementation(Dependencies.App.fragmentKTX)
    implementation(Dependencies.App.inject)
    implementation(Dependencies.App.retrofit)
    implementation(Dependencies.App.retrofitConverterGson)
    implementation(Dependencies.App.okhttpLoggingInterceptor)

}