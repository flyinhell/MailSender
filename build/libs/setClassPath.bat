rem Library路徑
SET LIB_HOME=%~dp0..\lib

rem 設定classpath
SET CLASSPATH=.
rem 避免 %%i 變數裡含有空格在傳遞參數時使用雙引號，確認參數不會因空格被截斷
FOR %%i IN ("%LIB_HOME%\*.jar") DO CALL :append "%%i"

:append
rem %~1 中的 ~ 表示要去掉變數裡的雙引號，此處雙引號必須去掉不然無法執行
SET CLASSPATH=%CLASSPATH%;%~1
GOTO :EOF
