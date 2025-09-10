plugins {
    java
    id("org.springframework.boot") version "3.5.0"
    id("io.spring.dependency-management") version "1.1.7"
    id("org.openapi.generator") version "7.12.0"
}

group = "ru.abramov.practicum"
version = "0.0.1-SNAPSHOT"

repositories {
    mavenCentral()
}

extra["springCloudVersion"] = "2025.0.0"

subprojects {
    apply(plugin = "java")
    apply(plugin = "io.spring.dependency-management")
    apply(plugin = "org.springframework.boot")
    apply(plugin = "org.openapi.generator")

    java {
        toolchain {
            languageVersion = JavaLanguageVersion.of(21)
        }
    }

    configurations {
        compileOnly {
            extendsFrom(configurations.annotationProcessor.get())
        }
    }

    dependencyManagement {
        imports {
            mavenBom("org.springframework.cloud:spring-cloud-dependencies:${property("springCloudVersion")}")
        }
    }

    repositories {
        mavenCentral()
    }

    dependencies {
        implementation("org.springframework.boot:spring-boot-starter-actuator")
        implementation("org.springframework.cloud:spring-cloud-starter-consul-discovery")

        compileOnly("org.projectlombok:lombok")
        developmentOnly("org.springframework.boot:spring-boot-devtools")
        annotationProcessor("org.projectlombok:lombok")

        testImplementation("org.springframework.boot:spring-boot-starter-test")
        testRuntimeOnly("org.junit.platform:junit-platform-launcher")
//
//
//        testImplementation("org.springframework.cloud:spring-cloud-starter-contract-stub-runner")

        implementation("org.springframework.cloud:spring-cloud-starter-openfeign")
        implementation("io.github.openfeign:feign-httpclient")
        implementation("io.github.resilience4j:resilience4j-all")
        implementation("io.github.resilience4j:resilience4j-circuitbreaker")
        implementation("io.github.resilience4j:resilience4j-timelimiter")
        implementation("io.github.resilience4j:resilience4j-retry")
        implementation("io.github.resilience4j:resilience4j-spring-boot3")

        implementation("io.github.openfeign:feign-jackson")
        implementation("io.swagger.core.v3:swagger-annotations:2.2.31")
        implementation("org.openapitools:jackson-databind-nullable:0.2.6")
        implementation("com.fasterxml.jackson.core:jackson-annotations")
    }


    tasks.withType<Test> {
        useJUnitPlatform()
    }
}