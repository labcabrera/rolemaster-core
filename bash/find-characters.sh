#!/bin/bash

curl -s \
  'http://localhost:8080/characters' \
  -H 'accept: application/json' \
  | python3 -m json.tool
