#!/bin/bash
set -e

rm -rf .deps
mkdir .deps
cd .deps

git clone git@github.com:mchahn/baselib.git
cd baselib
mvn install -Dmaven.test.skip=true
cd ..

git clone git@github.com:mchahn/udflib.git
cd udflib
mvn install -Dmaven.test.skip=true
cd ..

git clone git@github.com:mchahn/tclib.git
cd tclib
mvn install -Dmaven.test.skip=true
cd ..

echo "--- dependencies installed successfully ---"
