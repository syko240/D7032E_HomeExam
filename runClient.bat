@echo off
setlocal enabledelayedexpansion

:: Run the game
echo Running BoomerangGame...
java -cp bin;lib\json-simple-1.1.jar BoomerangGame 127.0.0.1 8080


:: End the script
exit /b %EXIT_CODE%