plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    id("com.google.dagger.hilt.android") version "2.48"
    id("com.google.devtools.ksp") version "1.9.22-1.0.16"
    id("androidx.navigation.safeargs.kotlin")
}

android {
    namespace = "com.example.driving_car_project"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.driving_car_project"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        testInstrumentationRunner = "com.example.driving_car_project.HiltTestRunner"
    }


    buildFeatures {
        viewBinding = true
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
    }
}

dependencies {

    implementation(project(":core-model"))
    implementation(project(":core-database"))
    implementation(project(":core-network"))
    implementation(project(":core-utils"))
    implementation(project(":core-di"))
    implementation(project(":core-ui"))
    implementation(project(":core-resources"))
    implementation(project(":core-navigation"))

    implementation(project(":feature-question"))
    implementation(project(":feature-exam"))
    implementation(project(":feature-history"))
    implementation(project(":feature-category"))
    implementation(project(":feature-guide"))
    implementation(project(":feature-home"))

    implementation(project(":infrastructure"))

    // Lifecycle
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.8.4")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.8.4")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.8.4")
    implementation("androidx.fragment:fragment-ktx:1.8.2")


    // Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.9.0")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.9.0")

    // Navigation
    implementation("androidx.navigation:navigation-fragment-ktx:2.8.1")
    implementation("androidx.navigation:navigation-ui-ktx:2.8.1")

    // Retrofit + OkHttp
    implementation("com.squareup.retrofit2:retrofit:2.11.0")
    implementation("com.squareup.retrofit2:converter-gson:2.11.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.12.0")

    // Room
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    implementation(libs.androidx.room.common)
    implementation(libs.androidx.espresso.contrib)
    ksp(libs.androidx.room.compiler)

    // Hilt
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)

    // Glide
    implementation(libs.glide)
    ksp(libs.glide.compiler)

    // RecyclerView
    implementation(libs.androidx.recyclerview)

    // AndroidX
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)

    // Testing
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    testImplementation (libs.mockito.core)
    testImplementation (libs.mockito.kotlin)
    //  để test room database
    testImplementation ("androidx.room:room-testing:2.5.1")
    testImplementation ("androidx.arch.core:core-testing:2.2.0")

//  để test với thư viện mockk
    testImplementation ("io.mockk:mockk-android:1.13.5")
    testImplementation ("io.mockk:mockk-agent:1.13.5")
//  cho test coroutine kotlin
    testImplementation ("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.1")

    androidTestImplementation("com.google.dagger:hilt-android-testing:2.48")
    kspAndroidTest("com.google.dagger:hilt-compiler:2.48")
    implementation(kotlin("script-runtime"))


}
