plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("kapt")
    id("dagger.hilt.android.plugin")
    id("kotlin-android")
}

android {
    compileSdkVersion(30)
    buildToolsVersion = "30.0.1"
    ndkVersion = "21.3.6528147"

    defaultConfig {
        applicationId = "com.example.retrofitroomsample"
        minSdkVersion(23)
        targetSdkVersion(30)
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        javaCompileOptions {
            annotationProcessorOptions {
                arguments.plusAssign(
                    mapOf(
                        "room.schemaLocation" to "$projectDir/schemas",
                        "room.incremental" to "true",
                        "room.expandProjection" to "true"
                    )
                )
            }
        }
    }

    buildTypes {
        named("release") {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        coreLibraryDesugaringEnabled = true
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_1_8.toString()
    }

    buildFeatures {
        viewBinding = true
    }

    lintOptions {
        isCheckDependencies = true
        isCheckGeneratedSources = true
    }

    splits {
        abi {
            isEnable = true
            isUniversalApk = true
        }
    }
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))

    // Kotlin / Java
    implementation(kotlin("stdlib-jdk7", "1.3.72"))
    coreLibraryDesugaring("com.android.tools:desugar_jdk_libs:1.0.9")

    // Android Architecture Components
    implementation("androidx.activity:activity-ktx:1.1.0")
    implementation("androidx.appcompat:appcompat:1.1.0")
    implementation("androidx.constraintlayout:constraintlayout:1.1.3")
    implementation("androidx.core:core-ktx:1.3.1")
    implementation("androidx.fragment:fragment-ktx:1.2.5")
    implementation("androidx.preference:preference-ktx:1.1.1")
    implementation("androidx.recyclerview:recyclerview:1.2.0-alpha05")
    implementation("androidx.swiperefreshlayout:swiperefreshlayout:1.1.0")

    // Glide
    val glideVersion = "4.11.0"
    implementation("com.github.bumptech.glide:glide:$glideVersion")
    kapt("com.github.bumptech.glide:compiler:$glideVersion")

    // Hilt
    val hiltVersion = "1.0.0-alpha02"
    val hiltAndroidVersion = "2.28.3-alpha"
    implementation("androidx.hilt:hilt-lifecycle-viewmodel:$hiltVersion")
    implementation("com.google.dagger:hilt-android:$hiltAndroidVersion")
    implementation("com.google.dagger:hilt-android:$hiltAndroidVersion")
    kapt("androidx.hilt:hilt-compiler:$hiltVersion")
    kapt("com.google.dagger:hilt-android-compiler:$hiltAndroidVersion")

    // Lifecycle
    val lifecycleVersion = "2.2.0"
    implementation("androidx.lifecycle:lifecycle-common-java8:$lifecycleVersion")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:$lifecycleVersion")
    implementation("androidx.lifecycle:lifecycle-process:$lifecycleVersion")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:$lifecycleVersion")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycleVersion")

    // Material Components
    implementation("com.google.android.material:material:1.2.0-rc01")

    // Navigation Component
    val navigationVersion = "2.3.0"
    implementation("androidx.navigation:navigation-fragment-ktx:$navigationVersion")
    implementation("androidx.navigation:navigation-ui-ktx:$navigationVersion")

    // Paging
    implementation("androidx.paging:paging-runtime-ktx:3.0.0-alpha03")

    // Retrofit
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-moshi:2.9.0")

    // Room
    val roomVersion = "2.2.5"
    implementation("androidx.room:room-runtime:$roomVersion")
    implementation("androidx.room:room-ktx:$roomVersion")
    kapt("androidx.room:room-compiler:$roomVersion")

    // Unit Testing
    testImplementation("junit:junit:4.13")
    androidTestImplementation("androidx.test.ext:junit-ktx:1.1.1")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.2.0")
}

kapt {
    correctErrorTypes = true
}