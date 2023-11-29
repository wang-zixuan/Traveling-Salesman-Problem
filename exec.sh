#!/bin/bash

# Check if the number of arguments provided is correct
if [ "$#" -ne 6 ] && [ "$#" -ne 8 ]; then
    echo "Usage: $0 -inst [file] -alg [algorithm] -time [time limit] (-seed [seed])"
    exit 1
fi

seed=""

# Parse command line arguments
while [[ "$#" -gt 0 ]]; do
    case $1 in
        -inst) file="$2"; shift ;;
        -alg) algorithm="$2"; shift ;;
        -time) time="$2"; shift ;;
        -seed) seed="$2"; shift ;;
        *) echo "Unknown parameter passed: $1"; exit 1 ;;
    esac
    shift
done

# Run Java program with provided arguments
cd src
javac Main.java
if [ -z "$seed" ]; then
    java Main -inst "$file" -alg "$algorithm" -time "$time"
else
    java Main -inst "$file" -alg "$algorithm" -time "$time" -seed "$seed"
fi