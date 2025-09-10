#!/bin/bash

set -e

# shellcheck disable=SC2046
export $(grep -v '^#' .env | xargs)

PIDS=()

### STEP 1: Сначала запускаем config
CONFIG_TASK="config:bootJar"
CONFIG_JAR_PATH="config/build/libs"

echo "➡️ Сборка config: ./gradlew $CONFIG_TASK"
./gradlew "$CONFIG_TASK"

CONFIG_JAR=$(ls "$CONFIG_JAR_PATH"/*.jar | grep -v 'original' | head -n 1)

if [[ -z "$CONFIG_JAR" ]]; then
  echo "❌ JAR-файл не найден для config в $CONFIG_JAR_PATH"
  exit 1
fi

echo "🚀 Запуск config-сервера: $CONFIG_JAR"
java -jar "$CONFIG_JAR" &

CONFIG_PID=$!
PIDS+=($CONFIG_PID)

# Ожидание старта config-сервера
echo "⏳ Ожидание запуска config-сервера на http://localhost:8888/actuator/health ..."
until curl -s "http://localhost:8888/actuator/health" | grep -q '"status":"UP"'; do
  if ! kill -0 "$CONFIG_PID" >/dev/null 2>&1; then
    echo "❌ Config-сервер завершился досрочно. PID $CONFIG_PID"
    exit 1
  fi
  echo "🔄 Config ещё не готов, проверяю снова через 2 сек..."
  sleep 2
done

echo "✅ Config-сервер работает!"


### STEP 2: Остальные сервисы
SERVICES=(
  "services:account:bootJar;services/account/build/libs"
  "services:blocker:bootJar;services/blocker/build/libs"
  "services:cash:bootJar;services/cash/build/libs"
  "services:exchange-generator:bootJar;services/exchange-generator/build/libs"
  "services:exchange:bootJar;services/exchange/build/libs"
  "services:front-ui:bootJar;services/front-ui/build/libs"
  "services:notification:bootJar;services/notification/build/libs"
  "services:transfer:bootJar;services/transfer/build/libs"
  "gateway:bootJar;gateway/build/libs"
)

for SERVICE_ENTRY in "${SERVICES[@]}"; do
  IFS=";" read -r TASK JAR_PATH <<< "$SERVICE_ENTRY"

  echo "➡️ Сборка: ./gradlew $TASK"
  ./gradlew "$TASK"

  JAR_FILE=$(find "$JAR_PATH" -maxdepth 1 -name "*.jar" ! -name "*original*" ! -name "*stubs*" | head -n 1)

  if [[ -z "$JAR_FILE" ]]; then
    echo "❌ JAR-файл не найден в $JAR_PATH"
    exit 1
  fi

  echo "✅ Найден JAR: $JAR_FILE. Запуск..."
  java -jar "$JAR_FILE" &
  PIDS+=($!)
done

trap "echo '🛑 Остановка всех процессов...'; kill ${PIDS[*]}" SIGINT SIGTERM

wait "${PIDS[@]}"
