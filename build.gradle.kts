import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

buildscript {
	repositories { mavenCentral() }
}

plugins {
	id("org.asciidoctor.jvm.convert") version "3.3.2"

	id("org.springframework.boot") version "3.0.2"
	id("io.spring.dependency-management") version "1.1.0"
	kotlin("jvm") version "1.8.10"
	kotlin("plugin.spring") version "1.8.10"
	id("com.epages.restdocs-api-spec") version "0.17.1"

	id("com.github.ben-manes.versions") version "+"
}

group = "com.example"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_17

repositories {
	mavenCentral()
}
dependencyManagement {
	imports {
		mavenBom("org.springframework.restdocs:spring-restdocs-bom:3.0.0")
	}
}


extra["snippetsDir"] = file("build/generated-snippets")

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-webflux")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("io.projectreactor.kotlin:reactor-kotlin-extensions")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
	implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")

	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("io.projectreactor:reactor-test")
	testImplementation("org.springframework.restdocs:spring-restdocs-webtestclient")
	// Without those the test works
	testImplementation("com.epages:restdocs-api-spec-webtestclient:0.17.1")
	testImplementation("com.epages:restdocs-api-spec:0.17.1")
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = "17"
	}
}
val snippetsDir by extra { file("${buildDir}/generated-snippets") }

tasks.withType<Test> {
	useJUnitPlatform()
}

//tasks.test {
//	outputs.dir(snippetsDir)
//}

//tasks.asciidoctor {
//	inputs.dir(snippetsdir)
//	dependson(test)
//}

openapi3 {
	title = "Hello World"
	description = "API Examples for Hello World"
	version = "0.1"
	format = "yaml"
	snippetsDirectory = snippetsDir.path
	outputDirectory = "src/docs/api-spec"
}