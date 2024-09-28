#!/bin/bash
set -e

mvn package
cp ./target/*.jar ~/Documents/TestServer/plugins/

rm -vfr ~/Documents/TestServer/world/datapacks/OurStory
cp -r ./datapack/OurStory ~/Documents/TestServer/world/datapacks
