#!/bin/bash

curl -X 'DELETE' -s \
  'http://localhost:8080/sessions' \
  -H 'accept: application/json' \
  | python3 -m json.tool
