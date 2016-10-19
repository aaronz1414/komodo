#!/usr/bin/env bash
javac -d bin -cp ".:./lib/*" ./src/com/aaronzalewski/*.java
java -cp "./bin:.:./lib/*" com.aaronzalewski.EmailCrawler $1