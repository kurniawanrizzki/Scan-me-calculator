import org.gradle.api.artifacts.dsl.DependencyHandler
import org.gradle.kotlin.dsl.project

object AppDependencies {
    val coreKtx by lazy { "androidx.core:core-ktx:${AppConstant.Version.KOTLIN_VERSION}" }
    val appCompat by lazy { "androidx.appcompat:appcompat:${AppConstant.Version.AndroidX.APPCOMPAT_VERSION}" }
    val material by lazy { "com.google.android.material:material:${AppConstant.Version.MATERIAL_VERSION}" }

    val navFragment by lazy { "androidx.navigation:navigation-fragment-ktx:${AppConstant.Version.AndroidX.NAV_VERSION}" }
    val navUi by lazy { "androidx.navigation:navigation-ui-ktx:${AppConstant.Version.AndroidX.NAV_VERSION}" }

    val exp4j by lazy { "net.objecthunter:exp4j:${AppConstant.Version.EXP4J_VERSION}" }

    val room by lazy { "androidx.room:room-runtime:${AppConstant.Version.AndroidX.ROOM_VERSION}" }
    val roomExt by lazy { "androidx.room:room-ktx:${AppConstant.Version.AndroidX.ROOM_VERSION}" }
    val roomCompiler by lazy { "androidx.room:room-compiler:${AppConstant.Version.AndroidX.ROOM_VERSION}" }

    val hilt by lazy { "com.google.dagger:hilt-android:${AppConstant.Version.HILT_VERSION}" }
    val hiltCompiler by lazy { "com.google.dagger:hilt-android-compiler:${AppConstant.Version.HILT_VERSION}" }

    val livedataKtx by lazy { "androidx.lifecycle:lifecycle-livedata-ktx:${AppConstant.Version.AndroidX.LIVEDATA_EXT_VERSION}" }

    val security by lazy { "androidx.security:security-crypto:${AppConstant.Version.AndroidX.SECURITY_CRYPTO_VERSION}" }
    val kotlinSerialization by lazy { "org.jetbrains.kotlinx:kotlinx-serialization-json:${AppConstant.Version.KOTLIN_SERIALIZATION_VERSION}" }

    val junit by lazy { "junit:junit:${AppConstant.Version.JUNIT_VERSION}" }
    val junitExt by lazy { "androidx.test.ext:junit:${AppConstant.Version.JUNIT_EXT_VERSION}" }
    val espresso by lazy { "androidx.test.espresso:espresso-core:${AppConstant.Version.ESPRESSO_VERSION}" }

    object Firebase {
        val bom by lazy { "com.google.firebase:firebase-bom:${AppConstant.Version.FIREBASE_VERSION}" }
        val analytics by lazy { "com.google.firebase:firebase-analytics-ktx" }
    }

    object StandaloneMLKit {
        val textRecognition by lazy { "com.google.android.gms:play-services-mlkit-text-recognition:${AppConstant.Version.TEXT_RECOGNITION_VERSION}" }
    }

    object Modules {
        const val CORE = ":core"
    }
}

fun DependencyHandler.androidx() = implementations(
    AppDependencies.coreKtx,
    AppDependencies.appCompat
)

fun DependencyHandler.room() {
    implementations(
        AppDependencies.room,
        AppDependencies.roomExt
    )
    kapts(AppDependencies.roomCompiler)
}

fun DependencyHandler.hilt() {
    implementations(AppDependencies.hilt)
    kapts(AppDependencies.hiltCompiler)
}

fun DependencyHandler.tests() {
    testImplementations(AppDependencies.junit)
    androidTestImplementations(
        AppDependencies.junitExt,
        AppDependencies.espresso
    )
}

fun DependencyHandler.libs(vararg modules: String) = modules.forEach {
    add("implementation", project(it))
}

fun DependencyHandler.implementations(vararg dependencies: String) = dependencies.forEach {
    add("implementation", it)
}

fun DependencyHandler.kapts(vararg dependencies: String) = dependencies.forEach {
    add("kapt", it)
}

fun DependencyHandler.testImplementations(vararg dependencies: String) = dependencies.forEach {
    add("testImplementation", it)
}

fun DependencyHandler.androidTestImplementations(vararg dependencies: String) = dependencies.forEach {
    add("androidTestImplementation", it)
}

fun DependencyHandler.annotationProcessors(vararg dependencies: String) = dependencies.forEach {
    add("annotationProcessor", it)
}