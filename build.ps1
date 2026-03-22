# Script di build per container backend e frontend
# Esegui da: .\build.ps1

$ErrorActionPreference = "Stop"
$root = $PSScriptRoot

Write-Host "Build security-commons (dipendenza)..."
Set-Location "$root\security-commons"
mvn install -DskipTests -q

Write-Host "Build inventory-service..."
Set-Location "$root\inventory-service"
docker build -t inventory-service:latest .

Write-Host "Build order-service..."
Set-Location "$root\order-service"
docker build -t order-service:latest .

Write-Host "Build frontend..."
Set-Location "$root\frontend"
docker build -t frontend:latest .

Set-Location $root
Write-Host "Build completato."
Write-Host "Per avviare: cd docker-config; docker compose up -d"
