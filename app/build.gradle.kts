import com.android.build.gradle.internal.api.BaseVariantOutputImpl
import kotlin.collections.listOf

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.gms.google-services")
    id("com.google.dagger.hilt.android")
    kotlin("kapt")
    kotlin("plugin.serialization")
}

android {
    namespace = AppConfig.applicationId
    compileSdk = AppConfig.compileSdkVersion

    defaultConfig {
        applicationId = AppConfig.applicationId
        minSdk = AppConfig.minSdkVersion
        targetSdk = AppConfig.targetSdkVersion
        versionCode = 100000
        versionName = "1.0.0"

        testInstrumentationRunner = AppConfig.testInstrumentRunner
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
        getByName("debug") {
            isMinifyEnabled = false
        }
    }

    flavorDimensions += listOf("theme", "provider")
    productFlavors {
        create("red") {
            dimension = "theme"
            applicationIdSuffix = ".red"
        }
        create("green") {
            dimension = "theme"
            applicationIdSuffix = ".green"
        }
        create("camera") {
            dimension = "provider"
            applicationIdSuffix = ".camera"
        }
        create("file") {
            dimension = "provider"
            applicationIdSuffix = ".file"
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_1_8.toString()
    }

    buildFeatures {
        dataBinding = true
    }

    applicationVariants.all {
        outputs.map { it as BaseVariantOutputImpl }
            .forEach { output ->
                var apkName = output.outputFile.name
                apkName = if (apkName.contains("camera")) apkName.replace("camera", "built-in-camera")
                else apkName.replace("file", "filesystem")

                if (apkName.contains("release")) {
                    apkName = apkName.replace("-release","").replace("-unsigned", "")
                        .replace("-signed", "")
                }

                output.outputFileName = apkName
            }
    }
}

dependencies {
    libs(AppDependencies.Modules.CORE)

    androidx()
    hilt()
    room()

    implementations(
        // material
        AppDependencies.material,

        // navigation
        AppDependencies.navFragment,
        AppDependencies.navUi,

        // standalone ml kit
        AppDependencies.StandaloneMLKit.textRecognition,

        // exp4j
        AppDependencies.exp4j,

        // livedata ktx extension
        AppDependencies.livedataKtx,

        // security crypto
        AppDependencies.security,

        // kotlin serialization
        AppDependencies.kotlinSerialization
    )

    tests()
}