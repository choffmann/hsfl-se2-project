plugins {
    kotlin("jvm") version "1.6.21" apply false
    kotlin("js") version "1.7.20" apply false
}

allprojects {
    group = "com.hsfl.springbreak"
    version = "0.0.1-SNAPSHOT"
    repositories {
        mavenCentral()
    }
}

buildscript {
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
    }
    dependencies {
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.6.21")
    }
}