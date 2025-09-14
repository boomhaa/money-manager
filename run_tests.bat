@echo off
REM Temporary workaround for Opera GX Java classpath corruption
REM This script temporarily modifies environment variables to run tests

echo Setting up clean Java environment...

REM Backup current PATH
set ORIGINAL_PATH=%PATH%

REM Create a clean PATH without Opera GX interference
set CLEAN_PATH=C:\Program Files\Java\jdk-21\bin;C:\Program Files\Java\jdk-21;C:\Windows\system32;C:\Windows;C:\Windows\System32\Wbem;C:\Windows\System32\WindowsPowerShell\v1.0\;C:\Windows\System32\OpenSSH\;C:\Program Files\Git\cmd;C:\Program Files\CMake\bin;C:\Program Files\nodejs\;C:\python\Scripts\;C:\python\;C:\Users\mycopm\AppData\Local\Microsoft\WindowsApps

REM Set clean environment
set PATH=%CLEAN_PATH%
set JAVA_HOME=C:\Program Files\Java\jdk-21
set GRADLE_OPTS=-Dfile.encoding=UTF-8 -Xmx2048m

echo Running tests with clean environment...
gradlew.bat testDebugUnitTest --no-daemon --max-workers=1

REM Restore original PATH
set PATH=%ORIGINAL_PATH%

echo Test execution completed.
pause

