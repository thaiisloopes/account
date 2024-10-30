plugins {
	kotlin("jvm") version "1.9.22"
	kotlin("plugin.spring") version "1.9.22"
	id("org.springframework.boot") version "3.3.5"
	id("io.spring.dependency-management") version "1.1.6"
	kotlin("plugin.jpa") version "1.9.22"
	id("io.gitlab.arturbosch.detekt") version "1.23.7"
	id("jacoco")
}

group = "com.caju"
version = "0.0.1-SNAPSHOT"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(17)
	}
}

jacoco {
	toolVersion = "0.8.7"
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.flywaydb:flyway-core")
	runtimeOnly("org.postgresql:postgresql")
	runtimeOnly("org.flywaydb:flyway-database-postgresql:10.20.1")
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
	testImplementation("io.mockk:mockk:1.13.12")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")

	detekt("io.gitlab.arturbosch.detekt:detekt-formatting:1.23.5")
	detekt("io.gitlab.arturbosch.detekt:detekt-cli:1.23.5")
}

kotlin {
	compilerOptions {
		freeCompilerArgs.addAll("-Xjsr305=strict")
	}
}

allOpen {
	annotation("jakarta.persistence.Entity")
	annotation("jakarta.persistence.MappedSuperclass")
	annotation("jakarta.persistence.Embeddable")
}

tasks.withType<Test> {
	useJUnitPlatform()
}

detekt {
	toolVersion = "1.23.5"
	input = files("./")
	config = files("./detekt-config.yml")
	autoCorrect = true
}

sourceSets {
	create("integrationTest") {
		java.srcDir("src/integrationTest/scala")
		resources.srcDir("src/integrationTest/resources")
		runtimeClasspath += sourceSets["main"].runtimeClasspath + sourceSets["test"].runtimeClasspath
		compileClasspath += sourceSets["main"].compileClasspath + sourceSets["test"].compileClasspath
	}
}

fun ignorePackagesInJacocoReport(classDirectories: ConfigurableFileCollection) {
	classDirectories.setFrom(
		files(
			classDirectories.files.map {
				fileTree(it).apply {
					exclude(
						"**/caju/**/*.kts",
						"**/caju/**/infra/**"
					)
				}
			}
		)
	)
}

tasks.register<JacocoReport>("jacocoReport") {
	sourceSets(sourceSets.main.get())
	executionData(fileTree(project.rootDir.absolutePath).include("**/build/jacoco/*.exec"))

	reports {
		xml.required.set(true)
		csv.required.set(false)
		html.required.set(true)
		html.outputLocation.set(layout.buildDirectory.dir("jacocoHtml"))
	}
	ignorePackagesInJacocoReport(classDirectories)
}
