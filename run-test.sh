#!/bin/bash

set -e

# shellcheck disable=SC2046
export $(grep -v '^#' .env | xargs)

./gradlew services:notification:verifierStubsJar
./gradlew services:notification:publishStubsPublicationToMavenLocal
./gradlew services:notification:contractTest

./gradlew services:account:verifierStubsJar
./gradlew services:account:publishStubsPublicationToMavenLocal
./gradlew services:account:contractTest

./gradlew services:blocker:verifierStubsJar
./gradlew services:blocker:publishStubsPublicationToMavenLocal
./gradlew services:blocker:contractTest

./gradlew services:cash:verifierStubsJar
./gradlew services:cash:publishStubsPublicationToMavenLocal
./gradlew services:cash:contractTest

./gradlew services:exchange:verifierStubsJar
./gradlew services:exchange:publishStubsPublicationToMavenLocal
./gradlew services:exchange:contractTest

./gradlew services:transfer:verifierStubsJar
./gradlew services:transfer:publishStubsPublicationToMavenLocal
./gradlew services:transfer:contractTest

./gradlew services:account:test
./gradlew services:blocker:test
./gradlew services:cash:test
./gradlew services:exchange:test
./gradlew services:exchange-generator:test
./gradlew services:front-ui:test
./gradlew services:notification:test
./gradlew services:transfer:test