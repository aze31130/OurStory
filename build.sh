#!/bin/bash
set -e
mvn package
cp ./target/*.jar ~/Documents/TestServer/plugins