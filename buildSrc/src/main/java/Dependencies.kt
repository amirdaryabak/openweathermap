object Dependencies {
    private const val navigationVersion = "2.3.0"
    const val hiltVersion = "2.38.1"
    private const val hiltJetpackVersion = "1.0.0-alpha03"
    private const val retrofitVersion = "2.9.0"
    private const val lifeCycleVersion = "2.2.0"

    object App {
        const val kotlinStdLib = "org.jetbrains.kotlin:kotlin-stdlib:${Versions.kotlinVersion}"
        const val core = "androidx.core:core-ktx:1.6.0"
        const val appCompat = "androidx.appcompat:appcompat:1.3.0"
        const val lifecycle = "androidx.lifecycle:lifecycle-runtime-ktx:2.3.0"
        const val material = "com.google.android.material:material:1.3.0"
        const val constraintLayout = "androidx.constraintlayout:constraintlayout:2.1.1"

        const val recyclerview = "androidx.recyclerview:recyclerview:1.1.0"
        const val navigationFragment =
            "androidx.navigation:navigation-fragment-ktx:$navigationVersion"
        const val navigationUi = "androidx.navigation:navigation-ui-ktx:$navigationVersion"
        const val hilt = "com.google.dagger:hilt-android:$hiltVersion"
        const val hiltCompiler = "com.google.dagger:hilt-android-compiler:$hiltVersion"
        const val hiltViewModel = "androidx.hilt:hilt-lifecycle-viewmodel:$hiltJetpackVersion"
        const val hiltJetpackCompiler = "androidx.hilt:hilt-compiler:$hiltJetpackVersion"
        const val viewModel = "androidx.lifecycle:lifecycle-viewmodel:$lifeCycleVersion"
        const val liveDataKtx = "androidx.lifecycle:lifecycle-livedata-ktx:$lifeCycleVersion"
        const val timber = "com.jakewharton.timber:timber:4.7.1"
        const val gms = "com.google.android.gms:play-services-maps:17.0.0"
        const val gmsLocation = "com.google.android.gms:play-services-location:17.0.0"
        const val gmsPlaces = "com.google.android.gms:play-services-places:17.0.0"
        const val gmsLibrariesPlaces = "com.google.android.libraries.places:places:2.3.0"
        const val legacySupport = "androidx.legacy:legacy-support-v4:1.0.0"
        const val lifecycleExtensions = "androidx.lifecycle:lifecycle-extensions:2.1.0"
        const val fragment = "androidx.fragment:fragment:1.2.5"
        const val activityKTX = "androidx.activity:activity-ktx:1.3.0-alpha07"
        const val fragmentKTX = "androidx.fragment:fragment-ktx:1.3.3"
        const val inject = "javax.inject:javax.inject:1"
        const val retrofit = "com.squareup.retrofit2:retrofit:$retrofitVersion"
        const val retrofitConverterGson = "com.squareup.retrofit2:converter-gson:$retrofitVersion"
        const val okhttpLoggingInterceptor = "com.squareup.okhttp3:logging-interceptor:4.9.0"
    }

}