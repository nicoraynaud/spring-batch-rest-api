#!/usr/bin/env bash

if [ "$1" == "" ] ||  [ "$2" == "" ]; then
    echo "Should pass the versions in parameter"
    exit 1
fi

sed -i '' "s/^version=.*/version=$1/g" gradle.properties

gradle clean build --no-daemon --refresh-dependencies

gradle createPom --no-daemon

git commit -a -m "Release version $1"
git tag -a "v$1" -m "Release version $1"

sed -i '' "s/^version=.*/version=$2/g" gradle.properties
git commit -a -m "New SNAPSHOT version $2"
git push origin HEAD:master
git push origin "v$1"
