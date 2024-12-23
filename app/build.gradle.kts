plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.google.android.libraries.mapsplatform.secrets.gradle.plugin)
}

android {
    namespace = "com.example.submissiondua"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.submissiondua"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
        buildConfigField("String", "BASE_URL", "\"https://story-api.dicoding.dev/v1/\"")

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
        viewBinding = true
        buildConfig = true
    }
}

dependencies {
    // Core Android Libraries
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.constraintlayout)

    // Lifecycle and ViewModel
    implementation(libs.androidx.lifecycle.livedata.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.androidx.lifecycle.livedata.ktx.v261)
    implementation(libs.androidx.lifecycle.viewmodel.ktx.v261)
    implementation(libs.androidx.lifecycle.livedata.ktx.v262)
    implementation(libs.androidx.lifecycle.viewmodel.ktx.v262)

    // Navigation
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)

    // DataStore
    implementation(libs.androidx.datastore.preferences)

    // Coroutines
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.kotlinx.coroutines.android)

    // Networking
    implementation(libs.retrofit2.retrofit)
    implementation(libs.retrofit2.converter.gson)
    implementation(libs.logging.interceptor)

    // Paging
    implementation(libs.androidx.paging.runtime.ktx)
    testImplementation(libs.androidx.paging.common)
    testImplementation("androidx.paging:paging-testing")
    testImplementation("androidx.paging:paging-runtime:3.1.0")

    // Camera
    implementation("androidx.camera:camera-core:1.4.1")
    implementation(libs.androidx.camera.camera2)
    implementation(libs.androidx.camera.lifecycle)
    implementation(libs.androidx.camera.view)
    implementation(libs.androidx.exifinterface)
    implementation(libs.androidx.camera.camera2.v110)
    implementation(libs.androidx.camera.lifecycle.v110)
    implementation("androidx.camera:camera-view:1.0.0-alpha14")

    // UI Components
    implementation(libs.androidx.activity.ktx)
    implementation(libs.glide)

    // Maps
    implementation(libs.play.services.maps)

    // Testing Dependencies
    testImplementation(libs.junit)
//    testImplementation("org.mockito:mockito-core:5.14.2")
//    testImplementation(libs.mockito.core)
//    testImplementation(libs.mockito.android)
    testImplementation(libs.androidx.core.testing)
    testImplementation(libs.jetbrains.kotlinx.coroutines.test)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.androidx.core.testing)
    androidTestImplementation(libs.jetbrains.kotlinx.coroutines.test)

    val mockito_version = "2.7.1"
    testImplementation("org.mockito:mockito-core:$mockito_version")
    testImplementation("org.mockito:mockito-inline:5.2.0")
    androidTestImplementation("org.mockito:mockito-android:$mockito_version")

}

//dependencies {
//
//    implementation(libs.androidx.core.ktx)
//    implementation(libs.androidx.appcompat)
//    implementation(libs.material)
//    implementation(libs.androidx.constraintlayout)
//    implementation(libs.androidx.lifecycle.livedata.ktx)
//    implementation(libs.androidx.lifecycle.viewmodel.ktx)
//    implementation(libs.androidx.navigation.fragment.ktx)
//    implementation(libs.androidx.navigation.ui.ktx)
//    implementation(libs.play.services.maps)
//    testImplementation(libs.junit)
//    testImplementation(libs.junit.junit)
//    testImplementation(libs.junit.junit)
//    testImplementation(libs.junit.junit)
//    androidTestImplementation(libs.androidx.junit)
//    androidTestImplementation(libs.androidx.espresso.core)
//    implementation(libs.androidx.datastore.preferences)
//    implementation(libs.androidx.lifecycle.viewmodel.ktx.v261)
//    implementation(libs.androidx.lifecycle.livedata.ktx.v261)
//    implementation(libs.androidx.activity.ktx)
//    implementation (libs.logging.interceptor)
//    implementation(libs.retrofit2.retrofit)
//    implementation(libs.retrofit2.converter.gson)
//    implementation(libs.androidx.lifecycle.viewmodel.ktx.v262)
//    implementation(libs.androidx.lifecycle.runtime.ktx)
//    implementation(libs.androidx.lifecycle.livedata.ktx.v262)
//    implementation(libs.androidx.lifecycle.viewmodel.compose)
//    implementation(libs.androidx.paging.runtime.ktx)
//    implementation(libs.androidx.camera.camera2)
//    implementation(libs.androidx.camera.lifecycle)
//    implementation(libs.androidx.camera.view)
//    implementation(libs.androidx.exifinterface)
//    implementation(libs.androidx.datastore.preferences)
//    implementation(libs.kotlinx.coroutines.core)
//    implementation(libs.kotlinx.coroutines.android)
//    implementation (libs.androidx.datastore.preferences)
//    implementation (libs.androidx.datastore.preferences)
//    implementation (libs.glide)
//    testImplementation ("org.mockito:mockito-core:5.14.2")
//    androidTestImplementation (libs.androidx.core.testing)
//    testImplementation (libs.androidx.core.testing)
//    androidTestImplementation (libs.jetbrains.kotlinx.coroutines.test)
//    testImplementation (libs.jetbrains.kotlinx.coroutines.test)
//    testImplementation (libs.mockito.core)
//    testImplementation (libs.mockito.android)
//    implementation (libs.androidx.paging.runtime)
//    testImplementation (libs.androidx.paging.common)
//    testImplementation ("androidx.paging:paging-testing")
//    testImplementation ("androidx.paging:paging-runtime:3.1.0")
//    implementation ("androidx.camera:camera-core:1.4.1")
//    implementation (libs.androidx.camera.camera2.v110)
//    implementation (libs.androidx.camera.lifecycle.v110)
//    implementation ("androidx.camera:camera-view:1.0.0-alpha14")
//}