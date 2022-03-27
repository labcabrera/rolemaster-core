#!/bin/bash

URL="http://localhost:8080/characters/$1"

curl -s -v \
  $URL \
  -H 'accept: application/json'
