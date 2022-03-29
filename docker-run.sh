#!/usr/bin/env bash

docker run -d --name rolemaster-core -e JAVA_OPTS="-Xmx256M -Xms128M" labcabrera/rolemaster-core:0.5.0-SNAPSHOT
