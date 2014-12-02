cd 0-prepareexport
@echo on crée les tables annexes sur l'AS400
call ant
cd ..
cd 1-export
@echo on rapatruie de l'AS400
call export.cmd
@echo Pour remplacer les  objectQuotingStrategy="QUOTE_ALL_OBJECTS" par rien
call ant
cd ..
set JAVA_OPTS=%JAVA_OPTS% -Xmx2048m
@echo on injecte
liquibase update
