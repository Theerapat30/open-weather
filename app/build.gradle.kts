import com.android.build.gradle.internal.api.BaseVariantOutputImpl
import java.util.Properties

/*
 * Copyright (C) 2022 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

@Suppress("DSL_SCOPE_VIOLATION") // Remove when fixed https://youtrack.jetbrains.com/issue/KTIJ-19369
plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.kapt)
    alias(libs.plugins.hilt.gradle)
    alias(libs.plugins.ksp)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.kotlin.serialization)
}

val localProps = Properties()
val localPropertiesFile = File(rootProject.rootDir, "openweather.properties")
if (localPropertiesFile.exists() && localPropertiesFile.isFile){
    localPropertiesFile.inputStream().use {
        localProps.load(it)
    }
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
}

android {
    namespace = "com.trp.open_weather"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.trp.open_weather"
        minSdk = 26
        targetSdk = 35
        versionCode = 1000100
        versionName = "1.0.1.0"
        val appName = "open-weather"
        val apkName = "${appName}_$versionName"

        buildOutputs.all {
            val variantOutputImpl = this as BaseVariantOutputImpl
            println("output type = ${variantOutputImpl.outputType}")
            println("output filename = ${variantOutputImpl.outputFileName}")
            variantOutputImpl.outputFileName = "${apkName}.apk"
        }

        testInstrumentationRunner = "com.trp.open_weather.HiltTestRunner"
        
        vectorDrawables {
            useSupportLibrary = true
        }

        // Enable room auto-migrations
        ksp {
            arg("room.schemaLocation", "$projectDir/schemas")
        }
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro", "retrofit2.pro")
            buildConfigField("String", "API_KEY", localProps.getProperty("API_KEY_PRD"))
        }
        getByName("debug") {
            buildConfigField("String", "API_KEY", localProps.getProperty("API_KEY_DEV"))
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
        compose = true
        aidl = false
        buildConfig = false
        renderScript = false
        shaders = false
        buildConfig = true
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }

}

dependencies {

    val composeBom = platform(libs.androidx.compose.bom)
    implementation(composeBom)
    androidTestImplementation(composeBom)

    // Core Android dependencies
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)

    // Hilt Dependency Injection
    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)
    // Hilt and instrumented tests.
    androidTestImplementation(libs.hilt.android.testing)
    kaptAndroidTest(libs.hilt.android.compiler)
    // Hilt and Robolectric tests.
    testImplementation(libs.hilt.android.testing)
    kaptTest(libs.hilt.android.compiler)

    // Arch Components
    implementation(libs.androidx.lifecycle.runtime.compose)
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.hilt.navigation.compose)
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    ksp(libs.androidx.room.compiler)

    // Compose
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)

    // Retrofit
    implementation(libs.retrofit.client)
    implementation(libs.retrofit.okhttp.logging)
//    implementation(libs.retrofit.converter)

    // Retrofit with Kotlin serialization Converter
    implementation("com.jakewharton.retrofit:retrofit2-kotlinx-serialization-converter:1.0.0")
    implementation("com.squareup.okhttp3:okhttp:4.11.0")
    // Kotlin serialization
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.5.1")

    implementation("com.google.accompanist:accompanist-permissions:0.37.0")
    implementation("com.google.android.gms:play-services-location:21.1.0")

    // Tooling
    debugImplementation(libs.androidx.compose.ui.tooling)
    // Instrumented tests
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    debugImplementation(libs.androidx.compose.ui.test.manifest)

    // Local tests: jUnit, coroutines, Android runner
    testImplementation(libs.junit)
    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.mockito.kotlin)
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.9.0")

    // Instrumented tests: jUnit rules and runners

    androidTestImplementation(libs.androidx.test.core)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.androidx.test.runner)
}
