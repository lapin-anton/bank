

dependencies {
    implementation("org.springframework.cloud:spring-cloud-config-server")
    implementation("org.springframework.boot:spring-boot-starter-web")
}


tasks.jar {
    enabled = false
}

tasks.bootJar {
    archiveFileName = "app.jar"
    mainClass.set("ru.yandex.practicum.bank.config.ConfigApplication")
}

tasks.bootRun {
    mainClass.set("ru.yandex.practicum.bank.config.ConfigApplication")
}
