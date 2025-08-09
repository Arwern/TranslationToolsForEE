#!/bin/sh

PLIK1="plik1.tra"
PLIK2="plik2.tra"
WYNIK="wynik.tra"

if [ ! -f "$PLIK1" ] || [ ! -f "$PLIK2" ]; then
    echo "Brakuje jednego z plików!"
    exit 1
fi

# Tworzymy tymczasowy plik wynikowy
> "$WYNIK"

while IFS= read -r linia || [ -n "$linia" ]; do
    case "$linia" in
        @*=\ ~* )
            numer=$(echo "$linia" | cut -d= -f1 | tr -d ' ')
            # Szukamy dokładnej linii z pliku2
            nowa_linia=$(grep "^$numer[[:space:]]*=" "$PLIK2")
            if [ -n "$nowa_linia" ]; then
                echo "$nowa_linia" >> "$WYNIK"
            else
                echo "$linia" >> "$WYNIK"
            fi
            ;;
        * )
            echo "$linia" >> "$WYNIK"
            ;;
    esac
done < "$PLIK1"

echo "Gotowe. Wynik zapisany w $WYNIK"
