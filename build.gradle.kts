plugins {
    //trick: for the same plugin versions in all sub-modules
    alias(libs.plugins.com.android.application) apply false
    alias(libs.plugins.com.android.library) apply false
    alias(libs.plugins.org.jetbrains.kotlin.android) apply false
    alias(libs.plugins.org.jetbrains.kotlin.multiplatform) apply false
    alias(libs.plugins.app.cash.sqldelight) apply false
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}
