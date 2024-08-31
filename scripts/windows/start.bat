@echo off
FOR /F "tokens=5 delims= " %%P IN ('netstat -a -n -o ^| findstr :8080.*LISTENING') DO TaskKill.exe /F /PID %%P
for %%i in (*.jar) do java -jar "%%i"