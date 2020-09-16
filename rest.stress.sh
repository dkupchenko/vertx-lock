#!/usr/bin/bash

while [[ "1" == "1" ]]
do
#    version=$( curl -s -X GET http://127.0.0.1:8008/hex )
#    echo -n '.'
    date
    curl -v -X GET http://127.0.0.1:8008/hex
    sleep 0.5
done
