@ECHO OFF

rem 切換到主目錄
cd %~dp0

rem 載入 classpath 的 bat 檔
CALL setClassPath.bat
title EmailSender

set JAVA_HOME="C:\Program Files\Java\java-1.8.0-openjdk-1.8.0.151_Mail"
set application_properties="application.properties"
%JAVA_HOME%\bin\java -Xms512m -Xmx1024m -Dfile.encoding=UTF-8 -jar -Dspring.config.location=%application_properties% EmailSenderApi.jar 
pause

