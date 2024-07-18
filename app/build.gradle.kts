plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("androidx.navigation.safeargs.kotlin")
    id("kotlin-parcelize")
    id("dagger.hilt.android.plugin")
    id("kotlin-kapt")
}

android {
    namespace = "com.example.boxgame"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.boxgame"
        minSdk = 30
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        debug {
            isDebuggable = true
            isMinifyEnabled = false
        }
        release {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    flavorDimensions += "default"
    productFlavors {
        create("stag") {
            dimension = "default"
            applicationIdSuffix = ".stag"
        }
        create("prod") {
            dimension = "default"
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        buildConfig = true
        viewBinding = true
        dataBinding = true
    }
    lint {
        disable += "MissingTranslation" + "TypographyFractions"
        abortOnError = false
        checkReleaseBuilds = false
    }
}

dependencies {

    implementation(libs.androidx.activity)
    val hilt_version = "2.45"
    implementation("org.jetbrains.kotlin:kotlin-stdlib:1.8.20")
    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.6.1")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.1")

    //Navigation Graph
    implementation("androidx.navigation:navigation-fragment-ktx:2.5.3")
    implementation("androidx.navigation:navigation-ui-ktx:2.5.3")

    // Hilt
    implementation("com.google.dagger:hilt-android:$hilt_version")
    kapt("com.google.dagger:hilt-android-compiler:$hilt_version")
    kapt("androidx.hilt:hilt-compiler:1.0.0")

    //crypto for encrypted shared preference
    implementation("androidx.security:security-crypto:1.1.0-alpha05")
    implementation("androidx.lifecycle:lifecycle-extensions:2.2.0")

    //sdp and ssp
    implementation("com.intuit.sdp:sdp-android:1.0.6")
    implementation("com.intuit.ssp:ssp-android:1.0.6")

    //Work Runtime
    implementation("androidx.work:work-runtime-ktx:2.8.1")

    //For Use Dependency on Activity and Fragment
    implementation("androidx.activity:activity-ktx:1.9.0")
    implementation("androidx.fragment:fragment-ktx:1.6.2")

    //lottie animation
    implementation("com.airbnb.android:lottie:5.0.3")
}