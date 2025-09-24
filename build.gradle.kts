//buildscript {
//        dependencies {
//classpath("com.google.dagger:hilt-android-gradle-plugin:2.52")
//        }
//}

plugins {
        alias(libs.plugins.android.application) apply false
        alias(libs.plugins.jetbrains.kotlin.android) apply false
        //alias(libs.plugins.hilt) apply false
        //alias(libs.plugins.ksp) apply false
        id("androidx.navigation.safeargs.kotlin") version "2.8.1" apply false
}
