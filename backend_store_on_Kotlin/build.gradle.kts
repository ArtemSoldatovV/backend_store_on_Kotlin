plugins {
    kotlin("jvm") version "2.0.21"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    implementation("io.ktor:ktor-server-core:2.3.0")
    implementation("io.ktor:ktor-server-netty:2.3.0")
    implementation("io.insert-koin:koin-ktor:3.4.0")

    implementation("org.flywaydb:flyway-core:9.15.1")

    implementation("org.postgresql:postgresql:42.3.8")
    implementation("org.jetbrains.exposed:_exposed-core:0.41.1")
    implementation("org.jetbrains.exposed:exposed-dao:0.41.1")
    implementation("org.jetbrains.exposed:exposed-jdbc:0.41.1")

    implementation("org.apache.kafka:kafka-clients:3.4.0")

    implementation("io.jsonwebtoken:jjwt-api:0.11.2")
    runtimeOnly("io.jsonwebtoken:jjwt-impl:0.11.2")
    runtimeOnly("io.jsonwebtoken:jjwt-jackson:0.11.2")

    implementation("redis.clients:jedis:4.3.1")

}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(20)
}

