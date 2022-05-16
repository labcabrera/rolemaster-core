#!/usr/bin/env bash

./gradlew clean :rolemaster-core-api:jibDockerBuild -x test
