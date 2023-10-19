@echo off
setlocal enabledelayedexpansion

:: Run the game
echo Running BoomerangGame...
java -cp bin;lib\json-simple-1.1.jar boomerang.BoomerangGame 8080 2 0


:: End the script
exit /b %EXIT_CODE%