@rem Licensed to the Apache Software Foundation (ASF)
@if "%DEBUG%"=="" @offset off
@if "%OS%"=="Windows_NT" setlocal
set DIRNAME=%~dp0
if "%DIRNAME%"=="" set DIRNAME=.
set APP_BASE_NAME=%~n0
set APP_HOME=%DIRNAME%
set DEFAULT_JVM_OPTS="-Xmx64m" "-Xms64m"
set JAVA_EXE=java.exe
%JAVA_EXE% -version >NUL 2>&1
if %ERRORLEVEL% equ 0 goto init
echo.
echo ERROR: JAVA_HOME is not set and no 'java' command could be found in your PATH.
goto fail
:init
set CLASSPATH=%APP_HOME%\gradle\wrapper\gradle-wrapper.jar
set COMMAND=%JAVA_EXE% %DEFAULT_JVM_OPTS% %JAVA_OPTS% %GRADLE_OPTS% "-Dorg.gradle.appname=%APP_BASE_NAME%" -classpath "%CLASSPATH%" org.gradle.wrapper.GradleWrapperMain %*
:execute
%COMMAND%
:fail
exit /b 1