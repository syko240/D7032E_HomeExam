@echo off

echo Compiling code...
javac -d bin src\boom\BoomerangAustralia.java
if ERRORLEVEL 1 goto error

echo Compiling tests...
javac -d bin -cp lib\org.junit4-4.3.1.jar;bin src\boom\BoomerangTest.java
if ERRORLEVEL 1 goto error

echo Compilation successful!
exit /b 0

:error
echo Compilation failed!
exit /b 1