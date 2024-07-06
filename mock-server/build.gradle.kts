plugins {
    kotlin("jvm") version "1.9.23"
    id("org.springframework.boot") version "2.7.0"
}

group = "com.vl.aviatickets"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web:2.7.0")
}

kotlin {
    jvmToolchain(17)
}