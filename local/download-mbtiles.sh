#!/bin/sh
set -e

MBTILES_FILE="${MBTILES_FILE:-/data/bright.mbtiles}"
MBTILES_URL="${MBTILES_URL:-https://github.com/maptiler/tileserver-gl/releases/download/v3.1.1/bright-v1.0.0.mbtiles}"

if [ ! -f "$MBTILES_FILE" ]; then
  echo "MBTiles file not found. Downloading from $MBTILES_URL..."
  mkdir -p "$(dirname "$MBTILES_FILE")"
  wget -O "$MBTILES_FILE" "$MBTILES_URL"
else
  echo "MBTiles file already exists at $MBTILES_FILE."
fi

exec docker-entrypoint.sh "$@"

