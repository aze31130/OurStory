#!/bin/bash
set -e

TARGET_DIR="./target"
PLUGIN_DIR="$HOME/Documents/TestServer/plugins"
DATAPACK_SRC="./datapack/OurStory"
DATAPACK_DEST="$HOME/Documents/TestServer/world/datapacks/OurStory"
SERVER_DIR="$HOME/Documents/TestServer"

# Build the project
mvn package

# Copy plugin into test server
cp "$TARGET_DIR"/*.jar "$PLUGIN_DIR/"

# Updates old Remove the old datapack and replace it with the new one
rm -vrf "$DATAPACK_DEST"
cp -r "$DATAPACK_SRC" "$DATAPACK_DEST"

# Start the test server
pushd "$SERVER_DIR"
java -jar paper-*.jar nogui
popd
