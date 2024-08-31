@echo off
set /p CurrentVersion=<version.txt

set "CurrentDirectory=%cd%"

del /F /Q upgrade-version.txt
bitsadmin.exe /transfer "Download Upgrade Version" https://github.com/rajajeswanth-capstone/my-diy-workshop/raw/main/src/main/resources/upgrade-version.txt %CurrentDirectory%/upgrade-version.txt

set /p UpgradeVersion=<upgrade-version.txt

IF %CurrentVersion%==%UpgradeVersion% (
	echo Already up-to-date
) ELSE (
	echo Current version is %CurrentVersion%
	echo Upgrade available to %UpgradeVersion%
	echo Follow the instructions on https://github.com/rajajeswanth-capstone to upgrade to %UpgradeVersion%
)

pause