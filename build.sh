#!/bin/bash
# Script di build per container backend e frontend
# Esegui: ./build.sh

set -e
ROOT="$(cd "$(dirname "$0")" && pwd)"

echo "Build security-commons (dipendenza)..."
cd "$ROOT/security-commons"
mvn install -DskipTests -q

echo "Build inventory-service..."
cd "$ROOT/inventory-service"
docker build -t daaddo/inventory-service:latest .

echo "Build order-service..."
cd "$ROOT/order-service"
docker build -t daaddo/order-service:latest .

echo "Build frontend..."
cd "$ROOT/frontend"
docker build -t daaddo/frontend-service:latest .

cd "$ROOT"
echo "Build completato."
echo "Per avviare: cd docker-config && docker compose up -d"
