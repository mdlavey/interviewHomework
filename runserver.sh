#!/bin/bash
mvn package > log.txt
cd Server
mvn package > log.txt
java -jar target/Server-1.0-SNAPSHOT.jar > log.txt