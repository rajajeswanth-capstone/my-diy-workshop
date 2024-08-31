@echo off
set "CurrentDirectory=%cd%"

cd ../
rmdir /s /q %CurrentDirectory%
pause