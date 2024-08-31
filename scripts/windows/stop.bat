@echo off
FOR /F "tokens=5 delims= " %%P IN ('netstat -a -n -o ^| findstr :8080.*LISTENING') DO TaskKill.exe /F /PID %%P
echo Shutdown Complete

pause