import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    kotlin("jvm") version "1.6.10"
    id("com.github.johnrengelman.shadow") version "7.1.0"
    application
}

application {
    mainClass.set("com.github.mckernant1.lol.esports.api.lambda.RunnerKt")
}

group = "com.github.mckernant1.lol.esports.api"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven {
        url = uri("https://mvn.mckernant1.com/release")
    }
}

dependencies {
    implementation(kotlin("reflect"))

    implementation("com.github.mckernant1.lol:esports-api:0.0.8")
    implementation("com.google.code.gson:gson:2.9.0")

    implementation("org.apache.logging.log4j:log4j-api:2.17.1")
    implementation("org.apache.logging.log4j:log4j-core:2.17.1")
    implementation("org.apache.logging.log4j:log4j-slf4j18-impl:2.17.1")
    implementation("com.amazonaws:aws-lambda-java-log4j2:1.5.1")

    testImplementation(kotlin("test"))

    implementation(platform("com.amazonaws:aws-java-sdk-bom:1.12.177"))
    implementation("com.amazonaws:aws-java-sdk-dynamodb")

    implementation(platform("software.amazon.awssdk:bom:2.17.148"))

    implementation("software.amazon.awssdk:dynamodb-enhanced")
    implementation("software.amazon.awssdk:dynamodb")
    implementation("software.amazon.awssdk:lambda")

    implementation("com.amazonaws:aws-lambda-java-core:1.2.1")

}

tasks.test {
    useJUnitPlatform()
}

tasks {
    compileKotlin {
        kotlinOptions.jvmTarget = "11"
    }
    compileTestKotlin {
        kotlinOptions.jvmTarget = "11"
    }
}

application {
    mainClass.set("MainKt")
}

tasks.withType<ShadowJar>() {
    manifest {
        attributes["Main-Class"] = "com.github.mckernant1.lol.esports.api.lambda.RunnerKt"
    }
}
