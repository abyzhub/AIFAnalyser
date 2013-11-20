@echo off
set H2_LIB_PATH=%~dp0
java -cp %H2_LIB_PATH%/lib/crm-junit*.jar org.h2.tools.Server -tcpShutdown tcp://localhost:9092 -tcpShutdownForce