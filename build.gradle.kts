plugins {
    kotlin("jvm") version "1.9.22"
    kotlin("plugin.allopen") version "1.9.22"

    // The following line allows to load io.gatling.gradle plugin and directly apply it
    id("io.gatling.gradle") version "3.15.0"
}

tasks.withType(JavaCompile::class) {
    options.release.set(19)
}

kotlin {
    compilerOptions {
        jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_19)
    }
}

gatling {
    enterprise.closureOf<Any> {
        // Enterprise Cloud (https://cloud.gatling.io/) configuration reference: https://docs.gatling.io/reference/integrations/build-tools/gradle-plugin/
    }
}

repositories {
    mavenCentral()
}
