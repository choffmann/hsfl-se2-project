plugins {
    kotlin("multiplatform")
    kotlin("plugin.serialization") version "1.7.21"
}

repositories {
    mavenCentral()
}

kotlin {
    jvm()
    js {
        nodejs()
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(kotlin("stdlib-common"))
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.4.1")
            }
        }

        val commonTest by getting {
            dependencies {
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
                //implementation("org.jetbrains.kotlinx:kotlinx-serialization-runtime-common:0.4.1")
            }
        }

        jvm().compilations["main"].defaultSourceSet {
            dependencies {
                implementation(kotlin("stdlib-jdk8"))
            }
        }

        jvm().compilations["test"].defaultSourceSet {
            dependencies {
                implementation(kotlin("test"))
                implementation(kotlin("test-junit"))
            }
        }

        js().compilations["main"].defaultSourceSet {
            dependencies {
                implementation(kotlin("stdlib-js"))
                //implementation("org.jetbrains.kotlinx:kotlinx-serialization-runtime-js:0.4.1")
            }
        }

        js().compilations["test"].defaultSourceSet {
            dependencies {
                implementation(kotlin("stdlib-js"))
                //implementation("org.jetbrains.kotlinx:kotlinx-serialization-runtime-js:0.4.1")
            }
        }
    }
}