@echo off
REM Script di build per container backend e frontend
REM Doppio-click o: build.cmd

cd /d "%~dp0"
powershell -ExecutionPolicy Bypass -File "%~dp0build.ps1"
pause
