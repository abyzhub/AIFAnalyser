@echo off
set H2_LIB_PATH=%~dp0
java -Xms256m -Xmx1024m -cp %H2_LIB_PATH%/lib/crm-junit*.jar org.h2.tools.Server -web -webPort 8082 -browser -tcp -tcpPort 9092 