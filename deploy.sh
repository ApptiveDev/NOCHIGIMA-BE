#! /bin/bash

set -euo pipefail

LOG_PATH=/home/ubuntu/logs
mkdir -p $LOG_PATH

cd /home/ubuntu/application

JAR=$(ls -t *.jar | head -n 1 || true)
if [ -z "$JAR" ]; then
    echo "$(date) JAR 파일을 찾을 수 없음" >&2
    exit 1
fi

CURRENT_APPLICATION_PID=$(ss -ntlp 'sport = :8080' | grep -oP 'pid=\K\d+' || true)
if [ "$CURRENT_APPLICATION_PID" ]; then
    echo "$(date) 기존 애플리케이션 종료 (PID: $CURRENT_APPLICATION_PID)"
    kill "$CURRENT_APPLICATION_PID"

    WAIT_COUNT=0
    while kill -0 "$CURRENT_APPLICATION_PID" 2>/dev/null; do
        if [ "$WAIT_COUNT" -ge 15 ]; then
            echo "$(date) 15초 동안 종료되지 않아 애플리케이션 강제 종료"
            kill -9 "$CURRENT_APPLICATION_PID"
            sleep 1
            break
        fi
        sleep 1
        ((WAIT_COUNT++))
    done
fi

echo "$(date) 애플리케이션 시작"
nohup java -jar "$JAR" > $LOG_PATH/application.log 2> $LOG_PATH/error.log &
