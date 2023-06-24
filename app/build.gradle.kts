plugins {
    id("org.springframework.boot") version "2.5.5"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
    kotlin("jvm") version "1.5.31"
}

group = "com"
version = "1.0-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

repositories {
    mavenLocal()
    mavenCentral()
    maven("https://jitpack.io")
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("com.fasterxml.jackson.core:jackson-databind")
    implementation("org.apache.httpcomponents:httpclient")
    implementation("com.github.everit-org.json-schema:org.everit.json.schema:1.11.1")
    implementation("org.slf4j:slf4j-api")
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}

tasks.jar {
    manifest {
        attributes["Main-Class"] = "com.java.server.App"
    }
}


// plugins {
//     // Apply the application plugin to add support for building a CLI application in Java.
//     application
// }

// repositories {
//     // Use Maven Central for resolving dependencies.
//     mavenCentral()
// }

// dependencies {
//     // Use JUnit Jupiter for testing.
//     testImplementation("org.junit.jupiter:junit-jupiter:5.9.1")

//     // This dependency is used by the application.
//     implementation("com.google.guava:guava:31.1-jre")

//     // Servlet API
//     implementation("javax.servlet:javax.servlet-api:4.0.1")

//     // JSON libraries (example: Jackson)
//     implementation("com.fasterxml.jackson.core:jackson-core:2.12.5")
//     implementation("com.fasterxml.jackson.core:jackson-databind:2.12.5")

//     // Jetty server
//     implementation("org.eclipse.jetty:jetty-server:9.4.43.v20210629")
// }

// application {
//     // Define the main class for the application.
//     mainClass.set("mastercard.dmp.take.home.App")
// }

// tasks.named<Test>("test") {
//     // Use JUnit Platform for unit tests.
//     useJUnitPlatform()
// }
