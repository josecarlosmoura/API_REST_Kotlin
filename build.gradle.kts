import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "3.2.2"
	id("io.spring.dependency-management") version "1.1.4"
	kotlin("jvm") version "1.9.22"
	kotlin("plugin.spring") version "1.9.22"
	kotlin("plugin.jpa") version "1.9.22"
	application
}

group = "br.com.alura"
version = "0.0.1-SNAPSHOT"
val mainClassName = "ForumApplication"

java {
	sourceCompatibility = JavaVersion.VERSION_17
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-validation")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.flywaydb:flyway-core:10.8.1")
	implementation("org.springframework.boot:spring-boot-starter-cache:3.2.2")
	implementation("org.springframework.boot:spring-boot-starter-security:3.2.5")
	implementation("io.jsonwebtoken:jjwt-api:0.12.5")
	developmentOnly("org.springframework.boot:spring-boot-devtools")
	developmentOnly("com.h2database:h2")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	runtimeOnly("io.jsonwebtoken:jjwt-impl:0.12.5")
	runtimeOnly("io.jsonwebtoken:jjwt-jackson:0.12.5")
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs += "-Xjsr305=strict"
		jvmTarget = "17"
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}
