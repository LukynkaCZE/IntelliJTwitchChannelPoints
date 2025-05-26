import org.jetbrains.intellij.platform.gradle.tasks.PrepareSandboxTask

plugins {
    id("java")
    kotlin("plugin.serialization") version "2.1.0"
    id("org.jetbrains.kotlin.jvm") version "2.1.0"
    id("org.jetbrains.intellij.platform") version "2.6.0"
}

group = "cz.lukynka"
version = "1.3.0"

repositories {
    repositories {
        mavenCentral()
        maven {
            name = "devOS"
            url = uri("https://mvn.devos.one/releases")
        }
        intellijPlatform {
            defaultRepositories()
        }
    }

    dependencies {
        implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.8.1")
        implementation("org.jetbrains.kotlinx:kotlinx-serialization-core:1.8.1")
        api("com.github.twitch4j:twitch4j:1.20.0")
        intellijPlatform {
            intellijIdeaCommunity("2025.1.1.1")
        }
    }

    tasks {
        // Set the JVM compatibility versions
        withType<JavaCompile> {
            sourceCompatibility = "21"
            targetCompatibility = "21"
        }
        withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
            kotlinOptions.jvmTarget = "21"
        }

        signPlugin {
            certificateChain.set(System.getenv("CERTIFICATE_CHAIN"))
            privateKey.set(System.getenv("PRIVATE_KEY"))
            password.set(System.getenv("PRIVATE_KEY_PASSWORD"))
        }

        withType<PrepareSandboxTask> {
            duplicatesStrategy = DuplicatesStrategy.EXCLUDE
            from(configurations.runtimeClasspath.get().map { if (it.isDirectory) it else zipTree(it) })
        }

        jar {
            duplicatesStrategy = DuplicatesStrategy.EXCLUDE
            from(configurations.runtimeClasspath.get().map { if (it.isDirectory) it else zipTree(it) })
        }

        buildPlugin {
            duplicatesStrategy = DuplicatesStrategy.EXCLUDE
            from(configurations.runtimeClasspath.get().map { if (it.isDirectory) it else zipTree(it) })
        }
    }
}