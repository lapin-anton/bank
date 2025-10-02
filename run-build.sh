#!/bin/bash

./gradlew services:account:bootJar
./gradlew services:blocker:bootJar
./gradlew services:cash:bootJar
./gradlew services:exchange-generator:bootJar
./gradlew services:exchange:bootJar
./gradlew services:front-ui:bootJar
./gradlew services:notification:bootJar
./gradlew services:transfer:bootJar