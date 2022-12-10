plugins {
    kotlin("js") version "1.7.21"
    kotlin("plugin.serialization") version "1.7.21"

    // To suppress build error processDceKotlinJs: invalid property id
    // https://youtrack.jetbrains.com/issue/KT-51921/KJS-invalid-property-id-on-processDceKotlinJs-when-external-enum-class-has-true-false-default-as-field-values
    id("io.github.turansky.kfc.legacy-union") version "5.8.0"
}

repositories {
    mavenCentral()
}

kotlin {
    js {
        moduleName = "frontend"
        browser {
            runTask {
                devServer = devServer?.copy(port = 3000)
            }
        }
        binaries.executable()
    }

    sourceSets {
        val main by getting {
            dependencies {
                // Coroutines & serialization
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.3")
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.4.1")

                // Ktor Client
                implementation("io.ktor:ktor-client-core:2.1.3")
                implementation("io.ktor:ktor-client-js:2.1.3")
                implementation("io.ktor:ktor-client-serialization:2.1.3")
                implementation("io.ktor:ktor-client-logging:2.1.3")
                implementation("io.ktor:ktor-client-content-negotiation:2.1.3")
                implementation("io.ktor:ktor-serialization-kotlinx-json:2.1.3")

                implementation("org.jetbrains.kotlin:kotlin-stdlib-js:1.7.21")
                implementation("org.jetbrains.kotlin-wrappers:kotlin-react:18.2.0-pre.447")
                implementation("org.jetbrains.kotlin-wrappers:kotlin-react-legacy:18.2.0-pre.447")
                implementation("org.jetbrains.kotlin-wrappers:kotlin-react-dom:18.2.0-pre.447")
                implementation("org.jetbrains.kotlin-wrappers:kotlin-react-router-dom:6.3.0-pre.447")
                implementation( "org.jetbrains.kotlin-wrappers:kotlin-redux:4.1.2-pre.447")
                implementation("org.jetbrains.kotlin-wrappers:kotlin-react-redux:7.2.6-pre.447")

                // Material Design Wrapper
                implementation("org.jetbrains.kotlin-wrappers:kotlin-emotion:11.10.5-pre.447")
                implementation("org.jetbrains.kotlin-wrappers:kotlin-mui:5.9.1-pre.447")
                implementation("org.jetbrains.kotlin-wrappers:kotlin-mui-icons:5.10.9-pre.447")

                // Kodein
                implementation("org.kodein.di:kodein-di:7.16.0")
            }
        }

        val test by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }
    }
}