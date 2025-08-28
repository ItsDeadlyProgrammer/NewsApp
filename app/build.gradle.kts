
import java.util.Properties
import java.io.FileInputStream

val localProperties = Properties()
val localPropertiesFile = rootProject.file("local.properties")
if (localPropertiesFile.exists()) {
    localProperties.load(FileInputStream(localPropertiesFile))
}

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    id("com.google.gms.google-services")

}



android {
    namespace = "com.example.news"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.example.news"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        val googleApiKey: String? = localProperties.getProperty("GOOGLE_API_KEY")
        buildConfigField("String", "GOOGLE_API_KEY", "\"${googleApiKey ?: ""}\"")
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
    implementation(libs.androidx.foundation)
    implementation(libs.androidx.animation.core.lint)
    implementation(libs.androidx.ui.text.google.fonts)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    // Compose UI
    implementation(libs.ui)
    implementation(libs.androidx.material)
    implementation(libs.ui.tooling.preview)
// Firebase Authentication
    implementation(libs.firebase.auth.ktx)
// Retrofit and Gson for API calls
    implementation(libs.retrofit)
    implementation(libs.converter.gson)
// Kotlin Coroutines
    implementation(libs.kotlinx.coroutines.android)
// Activity Compose for ViewModel support
    implementation(libs.androidx.activity.compose.v170)
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.coil.compose.v230)
    implementation(libs.androidx.material.v130)
    implementation(libs.accompanist.swiperefresh)


}