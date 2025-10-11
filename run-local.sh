#!/bin/bash

set -e

SERVICES=(
  "services:account:bootJar;services/account/build/libs;services/account/.env"
  "services:blocker:bootJar;services/blocker/build/libs;services/blocker/.env"
  "services:cash:bootJar;services/cash/build/libs;services/cash/.env"
  "services:exchange-generator:bootJar;services/exchange-generator/build/libs;services/exchange-generator/.env"
  "services:exchange:bootJar;services/exchange/build/libs;services/exchange/.env"
  "services:front-ui:bootJar;services/front-ui/build/libs;services/front-ui/.env"
  "services:notification:bootJar;services/notification/build/libs;services/notification/.env"
  "services:transfer:bootJar;services/transfer/build/libs;services/transfer/.env"
)

PIDS=()

for SERVICE_ENTRY in "${SERVICES[@]}"; do
  IFS=";" read -r TASK JAR_PATH ENV_PATH <<< "$SERVICE_ENTRY"

  echo "➡️ Сборка: ./gradlew $TASK"
  ./gradlew "$TASK"

  JAR_FILE=$(find "$JAR_PATH" -maxdepth 1 -name "*.jar" ! -name "*original*" ! -name "*stubs*" | head -n 1)

  if [[ -z "$JAR_FILE" ]]; then
    echo "❌ JAR-файл не найден в $JAR_PATH"
    exit 1
  fi

  echo "✅ Найден JAR: $JAR_FILE"

  # Загружаем переменные окружения (если есть .env)
  if [[ -f "$ENV_PATH" ]]; then
    echo "📦 Пробрасываем переменные из $ENV_PATH"
    # shellcheck disable=SC2046
    export $(grep -v '^#' "$ENV_PATH" | xargs)
  else
    echo "⚠️ Нет .env-файла для $TASK"
  fi

  echo "🚀 Запуск: java -jar $JAR_FILE"
  java -jar "$JAR_FILE" &
  PIDS+=($!)
done

trap "echo '🛑 Остановка всех процессов...'; kill ${PIDS[*]}" SIGINT SIGTERM

wait "${PIDS[@]}"
