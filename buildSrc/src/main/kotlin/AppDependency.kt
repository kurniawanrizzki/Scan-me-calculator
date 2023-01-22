import org.gradle.api.artifacts.dsl.DependencyHandler
import org.gradle.kotlin.dsl.project

object AppDependencies {
    val coreKtx by lazy { "androidx.core:core-ktx:${AppConstant.Version.AndroidX.CORE_KTX_VERSION}" }
    val appCompat by lazy { "androidx.appcompat:appcompat:${AppConstant.Version.AndroidX.APPCOMPAT_VERSION}" }
    val material by lazy { "com.google.android.material:material:${AppConstant.Version.MATERIAL_VERSION}" }
    val junit by lazy { "junit:junit:${AppConstant.Version.JUNIT_VERSION}" }
    val junitExt by lazy { "androidx.test.ext:junit:${AppConstant.Version.JUNIT_EXT_VERSION}" }
    val espresso by lazy { "androidx.test.espresso:espresso-core:${AppConstant.Version.ESPRESSO_VERSION}" }

    object Modules {
        const val CORE = ":core"
    }
}

fun DependencyHandler.androidx() = implementations(
    AppDependencies.coreKtx,
    AppDependencies.appCompat
)

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

fun DependencyHandler.testImplementations(vararg dependencies: String) = dependencies.forEach {
    add("testImplementation", it)
}

fun DependencyHandler.androidTestImplementations(vararg dependencies: String) = dependencies.forEach {
    add("androidTestImplementation", it)
}