import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

group = "bookmaker"
version = "0.1.0"

plugins {
    `java-library`
    application

    val kotlinVersion = "1.8.10"
    kotlin("jvm") version kotlinVersion
    kotlin("kapt") version kotlinVersion
}

allprojects {
    repositories {
        mavenCentral()
        maven("https://maven.reposilite.com/snapshots")
    }
}

subprojects {
    apply(plugin = "java-library")
    apply(plugin = "kotlin")
    apply(plugin = "application")

    dependencies {
        val junit = "5.9.0"
        testImplementation("org.junit.jupiter:junit-jupiter-params:$junit")
        testImplementation("org.junit.jupiter:junit-jupiter-api:$junit")
        testImplementation("org.junit.jupiter:junit-jupiter-engine:$junit")

        testImplementation("com.konghq:unirest-java:3.14.2")
        testImplementation("org.assertj:assertj-core:3.23.1")
        testImplementation("com.github.javafaker:javafaker:1.0.2")
    }

    tasks.withType<Test> {
        useJUnitPlatform()
    }

    java {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    tasks.withType<KotlinCompile>().configureEach {
        kotlinOptions {
            jvmTarget = "17"
            languageVersion = "1.8"
        }
    }
}