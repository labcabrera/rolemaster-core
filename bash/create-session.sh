#!/bin/bash

curl -X 'POST' -s \
  'http://localhost:8080/sessions?name=test-session' \
  -H 'accept: application/json' \
  | python3 -m json.tool
