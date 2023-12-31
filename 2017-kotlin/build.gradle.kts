buildscript {
//    ext.kotlin_version = '1.8.0'

    repositories {
        mavenCentral()
    }
    dependencies {
//        classpath "org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlin_version"
        classpath("org.junit.platform:junit-platform-gradle-plugin:1.0.2")
    }
}

plugins {
    kotlin("jvm") version "1.8.0"
}

group = "net.zomis"
version = "1.0-SNAPSHOT"

val junitJupiterVersion = "5.0.2"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation("org.junit.jupiter:junit-jupiter-api:${junitJupiterVersion}")
//    testRuntime("org.junit.jupiter:junit-jupiter-engine:${junitJupiterVersion}")
}
