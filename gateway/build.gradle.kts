

dependencies {
    implementation("org.springframework.cloud:spring-cloud-starter-gateway-server-webflux")
    implementation("org.springframework.cloud:spring-cloud-starter-config")
}

tasks.jar {
    enabled = false
}

tasks.bootJar {
    archiveFileName = "app.jar"
    mainClass.set("ru.yandex.practicum.bank.gateway.GatewayApplication")
}

tasks.bootRun {
    mainClass.set("ru.yandex.practicum.bank.gateway.GatewayApplication")
}