#! /bin/bash

set -euo pipefail

WAIT=0
MAX_WAIT=15
LOG_PATH=/home/ubuntu/logs

mkdir -p $LOG_PATH
cd /home/ubuntu/application

JAR=$(ls -t *.jar | head -n 1 || true)
if [ -z "$JAR" ]; then
    echo "$(date) JAR 파일을 찾을 수 없음"
    exit 1
fi

CURRENT_APPLICATION_PID=$(ss -ntlp 'sport = :8080' | grep -oP 'pid=\K\d+' || true)
if [ -z "$CURRENT_APPLICATION_PID" ]; then
    echo "$(date) 실행중인 애플리케이션이 없음"
else
    echo "$(date) 기존 애플리케이션 종료 (PID: $CURRENT_APPLICATION_PID)"
    kill "$CURRENT_APPLICATION_PID"

    while true; do
        if ! kill -0 "$CURRENT_APPLICATION_PID" 2>/dev/null; then
          break
        fi

        if [ "$WAIT" -ge "$MAX_WAIT" ]; then
          echo "$(date) ${MAX_WAIT}초 동안 종료되지 않아 애플리케이션 강제 종료"
          kill -9 "$CURRENT_APPLICATION_PID" 2>/dev/null || true
          sleep 1
          break
        fi

        sleep 1
        WAIT=$((WAIT + 1))
    done
fi

echo "$(date) 애플리케이션 시작"
nohup java -jar "$JAR" > $LOG_PATH/application.log 2> $LOG_PATH/error.log &
