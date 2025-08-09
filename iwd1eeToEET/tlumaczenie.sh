#!/bin/bash

INPUT_TRA="input.tra"
FULL_TRA="dialog.tra"
OUTPUT_TRA="output.tra"

if [ ! -f "$INPUT_TRA" ]; then
  echo "Brak pliku $INPUT_TRA"
  exit 1
fi

if [ ! -f "$FULL_TRA" ]; then
  echo "Brak pliku $FULL_TRA"
  exit 1
fi

# sortowanie numeryczne (-n), unikalne (-u)
refs=$(grep -o '^@[0-9]\+' "$INPUT_TRA" | tr -d '@' | sort -nu)

> "$OUTPUT_TRA"

for ref in $refs; do
  grep -E "^@$ref[[:space:]]*=" "$FULL_TRA" >> "$OUTPUT_TRA"
done

echo "Gotowe! Wygenerowano $OUTPUT_TRA"
