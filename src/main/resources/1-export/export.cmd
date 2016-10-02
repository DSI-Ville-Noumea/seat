del export.xml
del export.log
set JAVA_OPTS=%JAVA_OPTS% -Xmx2048m -Dfile.encoding=utf8
liquibase --changeLogFile=export.xml --defaultsFile=../AS400.properties --diffTypes="data" --logLevel=info --logFile=export.log generateChangeLog


