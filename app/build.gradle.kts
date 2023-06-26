plugins {
    id("org.springframework.boot") version "2.5.5"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
    kotlin("jvm") version "1.5.31"
    id("jacoco")
    id("scala")
    id("org.jetbrains.dokka") version "1.4.32"
}

group = "com"
version = "1.0"
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
    implementation("org.slf4j:slf4j-api:1.7.32")
    implementation("ch.qos.logback:logback-classic:1.2.6")

    testImplementation("io.gatling.highcharts:gatling-charts-highcharts:3.7.0")
    testImplementation("com.fasterxml.jackson.core:jackson-databind:2.13.0")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.7.2")
    testImplementation("com.github.tomakehurst:wiremock-jre8:2.31.0")
    testImplementation("org.mockito:mockito-core:3.12.4")
    testImplementation("org.mockito:mockito-junit-jupiter:3.12.4")
    testImplementation("org.powermock:powermock-api-mockito2:2.0.9")
    testImplementation("org.powermock:powermock-module-junit4:2.0.9")
    testImplementation("org.springframework.boot:spring-boot-starter-test") {
        exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
    }
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}

tasks.named<Test>("test") {
    exclude("**/TransactionControllerIntegrationTest.*")

    useJUnitPlatform()
    finalizedBy("jacocoTestReport")
}

tasks.named<JacocoReport>("jacocoTestReport") {
    dependsOn(tasks.test)
    reports {
        xml.isEnabled = true
        html.isEnabled = true
    }
}

tasks.withType<JacocoReport> {
    afterEvaluate {
        classDirectories.setFrom(files(classDirectories.files.map {
            fileTree(it).apply {
                exclude("**/ExternalApiServiceImpl.class")
            }
        }))
    }
}

tasks.withType<JacocoCoverageVerification> {
    afterEvaluate {
        classDirectories.setFrom(files(classDirectories.files.map {
            fileTree(it).apply {
                exclude("**/ExternalApiServiceImpl.class")
            }
        }))
    }
}

jacoco {
    toolVersion = "0.8.7"
}

tasks.register<JavaExec>("loadTest") {
    dependsOn(tasks.named("testClasses"))
    description = "Load Test With Gatling"
    group = "Load Test"
    classpath = sourceSets.named("test").get().runtimeClasspath
    jvmArgs("-Dgatling.core.directory.binaries=${sourceSets.named("test").get().output.classesDirs.asPath}")
    main = "io.gatling.app.Gatling"
    args = listOf(
        "--simulation", "BlazeMeterGatlingTest",
        "--results-folder", "${buildDir}/gatling-results",
        "--binaries-folder", sourceSets.named("test").get().output.classesDirs.asPath,
        "--bodies-folder", sourceSets.named("test").get().resources.srcDirs.toList().first().toString() + "/gatling/bodies"
    )
}

// tasks.jar {
//     manifest {
//         attributes["Main-Class"] = "com.java.server.App"
//     }
// }

// val jarVersion: String by project
// tasks.jar {
//     manifest {
//         attributes["Main-Class"] = "com.App"
//     }
//     archiveFileName.set("fraud-detection-service-$version.jar")
// }
