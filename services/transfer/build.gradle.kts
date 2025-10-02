plugins {
    id("org.springframework.cloud.contract") version "4.1.3"
    id("maven-publish")
}

dependencies {


    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.5.0")

    implementation("org.springframework.retry:spring-retry")
    implementation("org.springframework.boot:spring-boot-starter-aop")

    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.security:spring-security-oauth2-jose")
    implementation("org.springframework.boot:spring-boot-starter-oauth2-resource-server")

    testImplementation("org.springframework.cloud:spring-cloud-starter-contract-verifier")
    testImplementation("org.springframework.cloud:spring-cloud-starter-contract-stub-runner")

    implementation(project(":common"))
    implementation(project(":clients:account"))
    implementation(project(":clients:exchange"))
    implementation(project(":clients:blocker"))
    implementation(project(":clients:notification"))
}

contracts {
    contractsDslDir.set(file("$rootDir/services/transfer/src/test/resources/contracts"))
    baseClassForTests.set("ru.yandex.practicum.bank.service.transfer.contract.BaseContractTest")
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
    inputSpec.set("$rootDir/services/transfer/src/main/resources/openapi.yaml")
    outputDir.set("$rootDir/clients/transfer")
    apiPackage.set("ru.yandex.practicum.bank.client.transfer.api")
    modelPackage.set("ru.yandex.practicum.bank.client.transfer.model")
    invokerPackage.set("ru.yandex.practicum.bank.client.transfer.invoker")
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
    mainClass.set("ru.yandex.practicum.bank.service.transfer.TransferServicePracticumBankApplication")
}

tasks.bootRun {
    mainClass.set("ru.yandex.practicum.bank.service.transfer.TransferServicePracticumBankApplication")
}
