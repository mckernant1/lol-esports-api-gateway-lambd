import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.6.10"
    application
}

group = "com.github.mckernant1.lol.esports.api"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    jcenter()
    maven {
        url = uri("https://mvn.mckernant1.com/release")
    }
}

dependencies {
    implementation("com.github.mckernant1.lol:esports-api:0.0.6")
    implementation("com.google.code.gson:gson:2.9.0")

    implementation("org.apache.logging.log4j:log4j-api:2.17.1")
    implementation("org.apache.logging.log4j:log4j-core:2.17.1")
    implementation("org.apache.logging.log4j:log4j-slf4j18-impl:2.17.1")
    implementation("com.amazonaws:aws-lambda-java-log4j2:1.5.1")

    testImplementation(kotlin("test"))

    implementation(platform("software.amazon.awssdk:bom:2.17.148"))

    implementation("software.amazon.awssdk:dynamodb-enhanced")
    implementation("software.amazon.awssdk:lambda")

}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "11"
}

application {
    mainClass.set("MainKt")
}
