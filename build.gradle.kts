import org.gradle.kotlin.dsl.*
import io.spring.gradle.dependencymanagement.dsl.DependencyManagementExtension
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.springframework.boot.gradle.plugin.SpringBootPlugin.*
import org.gradle.api.tasks.wrapper.Wrapper.DistributionType.BIN

val springCloudVersion by extra { "Hoxton.RELEASE" }
val kotlinVersion = "1.3.61"

buildscript {
    repositories {
        mavenCentral()
        maven("https://repo.spring.io/snapshot")
        maven("https://repo.spring.io/milestone")
    }
}

plugins {
	idea
    val kotlinVersion = "1.3.61"
    kotlin("jvm") version kotlinVersion
    kotlin("plugin.spring") version kotlinVersion
    id("org.springframework.boot") version "2.2.1.RELEASE"
}
apply {
    plugin("io.spring.dependency-management")
}

group = "com.spring.reproducer"
version = "0.0.1-SNAPSHOT"

tasks.withType<KotlinCompile> {
    kotlinOptions {
        jvmTarget = "1.8"
        freeCompilerArgs = listOf("-Xjsr305=strict")
    }
}

repositories {
    mavenCentral()
    maven("https://repo.spring.io/snapshot")
    maven("https://repo.spring.io/milestone")
}

the<DependencyManagementExtension>().apply {
    imports {
        mavenBom(BOM_COORDINATES) { bomProperty("kotlin.version", kotlinVersion) }
        mavenBom("org.springframework.cloud:spring-cloud-dependencies:$springCloudVersion")
    }
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-webflux")
    implementation("org.springframework.cloud:spring-cloud-starter-sleuth")
    implementation("io.opentracing.brave:brave-opentracing") { exclude(module = "brave-tests") }
    implementation("io.zipkin.brave:brave")
    implementation(kotlin("stdlib-jdk8"))
    implementation(kotlin("reflect"))
    implementation("io.projectreactor.kotlin:reactor-kotlin-extensions")
    testImplementation("io.projectreactor:reactor-test")
    testImplementation("io.rest-assured:spring-web-test-client")
    testImplementation("org.junit.jupiter:junit-jupiter-api")
    testImplementation("org.springframework.boot:spring-boot-starter-test") { exclude(module = "junit") }
    testRuntime("org.junit.jupiter:junit-jupiter-engine")
}

tasks.withType<Test> { useJUnitPlatform() }

tasks.withType<Wrapper>().configureEach {
    gradleVersion = "6.0.1"
    distributionType = BIN
}