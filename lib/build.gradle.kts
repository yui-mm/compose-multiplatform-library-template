plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.kotlinCocoapods)
    alias(libs.plugins.mavenPublish)
}

kotlin {
    androidTarget {
        // Android build variants
        publishLibraryVariants("release", "debug")
    }

    listOf(
        iosX64(), iosArm64(), iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "ExampleLibrary"
            isStatic = true
        }
    }

    cocoapods {
        version = "1.0"
        ios.deploymentTarget = "16.0"
        summary = "Some description for a Kotlin/Native module"
        homepage = "Link to a Kotlin/Native module homepage"
        framework {
            binaryOptions["bundleId"] = "com.example.library"
            baseName = "ExampleLibrary"
            isStatic = true
        }
    }

    sourceSets {
        androidMain.dependencies {
            implementation(compose.preview)
        }
        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)
            implementation(libs.androidx.lifecycle.viewmodel)
            implementation(libs.androidx.lifecycle.runtime.compose)
        }
    }
}

android {
    namespace = "com.cmp.template.app"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }
}

dependencies {
    debugImplementation(compose.uiTooling)
}

/** maven pom */
publishing {
    publications {
        create<MavenPublication>("maven") {
            from(components["kotlin"])
            groupId = "com.example.lib"
            artifactId = "artfifact-id"
            version = "0.0.1"

            pom {
                name.set("Compose Multiplatform library template")
                description.set("This is a library template for Compose Multiplatform.")
                url.set("https://github.com/yui-mm/compose-multiplatform-library-template")
                licenses {
                    license {
                        name.set("The Apache License, Version 2.0")
                        url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
                    }
                }
                developers {
                    developer {
                        id.set("yui-mm")
                        name.set("yui-mm")
                        email.set("yui-mm@gmail.com")
                    }
                }

                scm {
                    connection.set("scm:git:git://github.com/yui-mm/compose-multiplatform-library-template.git")
                    developerConnection.set("scm:git:ssh://git@github.com:yui-mm/compose-multiplatform-library-template.git")
                    url.set("https://github.com/yui-mm/compose-multiplatform-library-template")
                }
            }
        }
    }
    repositories {
        mavenLocal()
    }
}
