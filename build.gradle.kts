// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version AppConstant.Version.AGP_VERSION apply false
    id("com.android.library") version AppConstant.Version.AGP_VERSION apply false
    id("org.jetbrains.kotlin.android") version AppConstant.Version.KOTLIN_VERSION apply false
    id("com.google.gms.google-services") version AppConstant.Version.GOOGLE_SERVICES_VERSION apply false
    id("com.google.dagger.hilt.android") version AppConstant.Version.HILT_VERSION apply false
    kotlin("jvm") version AppConstant.Version.KOTLIN_VERSION apply false
    kotlin("plugin.serialization") version AppConstant.Version.KOTLIN_VERSION apply false
}