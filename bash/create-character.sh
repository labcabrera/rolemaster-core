#!/bin/bash

curl -X 'POST' -s \
  'http://localhost:8080/characters' \
  -H 'accept: */*' \
  -H 'Content-Type: application/json' \
  -d '{
  "name": "Kiove",
  "raceId": "common-men",
  "attributesRoll": 660,
  "baseAttributes": {
    "ag": 96,
    "co": 90,
    "me": 38,
    "re": 43,
    "sd": 39,
    "em": 20,
    "in": 90,
    "pr": 50,
    "st": 92,
    "qu": 75
  }
}' | python3 -m json.tool