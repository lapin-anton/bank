#!/bin/bash

function load_env_and_run() {
  local env_file=$1
  shift
  echo "Loading env from: $env_file"

  if [ ! -f "$env_file" ]; then
    echo "File not found: $env_file"
    exit 1
  fi

  set -a
  # shellcheck source=/dev/null
  source "$env_file"
  set +a

  echo "Env loaded. Running Gradle tasks..."
  "$@"
}

load_env_and_run services/notification/.env ./gradlew services:notification:verifierStubsJar
load_env_and_run services/notification/.env ./gradlew services:notification:publishStubsPublicationToMavenLocal
load_env_and_run services/notification/.env ./gradlew services:notification:contractTest

load_env_and_run services/account/.env ./gradlew services:account:verifierStubsJar
load_env_and_run services/account/.env ./gradlew services:account:publishStubsPublicationToMavenLocal
load_env_and_run services/account/.env ./gradlew services:account:contractTest

load_env_and_run services/blocker/.env ./gradlew services:blocker:verifierStubsJar
load_env_and_run services/blocker/.env ./gradlew services:blocker:publishStubsPublicationToMavenLocal
load_env_and_run services/blocker/.env ./gradlew services:blocker:contractTest

load_env_and_run services/cash/.env ./gradlew services:cash:verifierStubsJar
load_env_and_run services/cash/.env ./gradlew services:cash:publishStubsPublicationToMavenLocal
load_env_and_run services/cash/.env ./gradlew services:cash:contractTest

load_env_and_run services/exchange/.env ./gradlew services:exchange:verifierStubsJar
load_env_and_run services/exchange/.env ./gradlew services:exchange:publishStubsPublicationToMavenLocal
load_env_and_run services/exchange/.env ./gradlew services:exchange:contractTest

load_env_and_run services/transfer/.env ./gradlew services:transfer:verifierStubsJar
load_env_and_run services/transfer/.env ./gradlew services:transfer:publishStubsPublicationToMavenLocal
load_env_and_run services/transfer/.env ./gradlew services:transfer:contractTest

load_env_and_run services/account/.env ./gradlew services:account:test
load_env_and_run services/blocker/.env ./gradlew services:blocker:test
load_env_and_run services/cash/.env ./gradlew services:cash:test
load_env_and_run services/exchange/.env ./gradlew services:exchange:test
load_env_and_run services/exchange-generator/.env ./gradlew services:exchange-generator:test
load_env_and_run services/front-ui/.env ./gradlew services:front-ui:test
load_env_and_run services/notification/.env ./gradlew services:notification:test
load_env_and_run services/transfer/.env ./gradlew services:transfer:test