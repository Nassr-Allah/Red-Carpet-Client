plugins {
    //trick: for the same plugin versions in all sub-modules
    id("com.android.application").version("8.7.3").apply(false)
    id("com.android.library").version("8.7.3").apply(false)
    kotlin("android").version("2.1.0").apply(false)
    kotlin("multiplatform").version("2.1.0").apply(false)
    id ("com.google.devtools.ksp").version("2.1.0-1.0.29")
    id("org.jetbrains.kotlin.plugin.compose") version "2.1.0" apply false
}

buildscript {
    val compose_version by extra("1.1.1")
    val sqlDelightVersion = "1.5.5"
    dependencies {
        classpath("com.squareup.sqldelight:gradle-plugin:$sqlDelightVersion")
        classpath("com.google.dagger:hilt-android-gradle-plugin:2.51.1")
        classpath ("com.google.gms:google-services:4.4.2")
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}
