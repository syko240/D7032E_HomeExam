@echo off

echo Compiling code...
javac -d bin src\old\BoomerangAustralia.java
if ERRORLEVEL 1 goto error

echo Compiling tests...
javac -d bin -cp lib\org.junit4-4.3.1.jar;bin test\old\BoomerangTest.java
if ERRORLEVEL 1 goto error

echo Compilation successful!
exit /b 0

:error
echo Compilation failed!
exit /b 1