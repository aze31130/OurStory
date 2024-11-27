#!/bin/bash
set -e

TARGET_DIR="./target"
DATAPACK_SRC="./datapack/OurStory"
SERVER_DIR="$HOME/Documents/TestServer"

PLUGIN_DIR="$SERVER_DIR/plugins"
DATAPACK_DEST="$SERVER_DIR/world/datapacks/OurStory"

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
