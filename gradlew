#!/usr/bin/env sh
# Gradle start up script - simplified for GitHub Actions
# This will use system gradle if wrapper jar missing
set -e
if [ -f "gradle/wrapper/gradle-wrapper.jar" ]; then
  exec java -jar gradle/wrapper/gradle-wrapper.jar "$@"
else
  exec gradle "$@"
fi
