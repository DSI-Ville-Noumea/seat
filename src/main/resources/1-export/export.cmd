del export.xml
del export.log
set JAVA_OPTS=%JAVA_OPTS% -Xmx2048m -Dfile.encoding=utf8
rem liquibase --changeLogFile=export.xml --driver=com.ibm.as400.access.AS400JDBCDriver --diffTypes="data" --url="jdbc:as400://robinnw;errors=full;date format=iso" --username=***REMOVED*** --password=***REMOVED*** --defaultSchemaName=seat --logLevel=info --logFile=export.log generateChangeLog 
liquibase --changeLogFile=export.xml --defaultsFile=../AS400.properties --diffTypes="data" --logLevel=info --logFile=export.log generateChangeLog


