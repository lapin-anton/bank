
plugins {
    id("org.springframework.cloud.contract") version "4.1.3"
    id("maven-publish")
}

dependencies {
    implementation("org.springframework.cloud:spring-cloud-starter-config")
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.5.0")

    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.security:spring-security-oauth2-jose")
    implementation("org.springframework.boot:spring-boot-starter-oauth2-resource-server")

    implementation("org.springframework.boot:spring-boot-starter-mail")

    implementation(project(":common"))

    testImplementation("org.springframework.cloud:spring-cloud-starter-contract-verifier")
}

contracts {
    contractsDslDir.set(file("$rootDir/services/notification/src/test/resources/contracts"))
    baseClassForTests.set("ru.yandex.practicum.bank.service.notification.contract.BaseContractTest")
}

publishing {
    publications {
        create<MavenPublication>("stubs") {
            groupId = "ru.yandex.practicum.bank"
            artifactId = "${project.name}-stubs"
            version = "0.0.1-SNAPSHOT"

            artifact(tasks.named("verifierStubsJar"))
        }
    }

    repositories {
        mavenLocal()
    }
}

openApiGenerate {
    generatorName.set("spring")
    inputSpec.set("$rootDir/services/notification/src/main/resources/openapi.yaml")
    outputDir.set("$rootDir/clients/notification")
    apiPackage.set("ru.yandex.practicum.bank.client.notification.api")
    modelPackage.set("ru.yandex.practicum.bank.client.notification.model")
    invokerPackage.set("ru.yandex.practicum.bank.client.notification.invoker")
    configOptions.set(
        mapOf(
            "library" to "spring-cloud",
            "useResponseEntity" to "false",
            "useTags" to "true",
            "dateLibrary" to "java8",
            "useJakartaEe" to "true"
        )
    )
}

tasks.jar {
    enabled = false
}

tasks.bootJar {
    archiveFileName = "app.jar"
    mainClass.set("ru.yandex.practicum.bank.service.notification.NotificationServicePracticumBankApplication")
}

tasks.bootRun {
    mainClass.set("ru.yandex.practicum.bank.service.notification.NotificationServicePracticumBankApplication")
}
