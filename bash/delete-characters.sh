#!/bin/bash

curl -X 'DELETE' -s \
  'http://localhost:8080/characters' \
  -H 'accept: application/json'
