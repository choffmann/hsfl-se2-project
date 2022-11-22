plugins {
    kotlin("js") version "1.7.20"
    kotlin("plugin.serialization") version "1.7.21"
}

repositories {
    mavenCentral()
}

kotlin {
    target {
        useCommonJs()
        browser()
    }

    sourceSets {
        val main by getting {
            dependencies {
                // Coroutines & serialization
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.3")
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.3.3")

                // Ktor
                implementation("io.ktor:ktor-client-core:2.1.3")
                implementation("io.ktor:ktor-client-js:2.1.3")
                implementation("io.ktor:ktor-client-serialization:2.1.3")
                implementation("io.ktor:ktor-client-logging:2.1.3")
                implementation("io.ktor:ktor-client-content-negotiation:2.1.3")
                implementation("io.ktor:ktor-serialization-kotlinx-json:2.1.3")

                implementation("org.jetbrains.kotlin-wrappers:kotlin-react:18.2.0-pre.346")
                implementation("org.jetbrains.kotlin-wrappers:kotlin-react-dom:18.2.0-pre.346")
                implementation("org.jetbrains.kotlin-wrappers:kotlin-emotion:11.9.3-pre.346")
                implementation("org.jetbrains.kotlin-wrappers:kotlin-react-router-dom:6.3.0-pre.346")
                implementation( "org.jetbrains.kotlin-wrappers:kotlin-redux:4.1.2-pre.346")
                implementation("org.jetbrains.kotlin-wrappers:kotlin-react-redux:7.2.6-pre.346")

                // Material Design Wrapper
                implementation("org.jetbrains.kotlin-wrappers:kotlin-emotion:11.10.5-pre.434")
                implementation("org.jetbrains.kotlin-wrappers:kotlin-mui:5.9.1-pre.435")
                implementation("org.jetbrains.kotlin-wrappers:kotlin-mui-icons:5.10.9-pre.434")

                implementation(project(":shared"))
            }
        }

        val test by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }
    }
}