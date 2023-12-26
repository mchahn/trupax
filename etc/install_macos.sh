#!/bin/bash
set -e

if [ $# -eq 0 ]
then
  echo "usage: $0 [install-dir]"
  echo "example: $0 /opt/bin"
  exit 1
fi

INSTALL_DIR=$1

cp -va ../target/trupax-*-with-dependencies.jar $1/trupax.jar
cp -va trupax_example.properties                $1/trupax.properties
cp -va trupax                                   $1/trupax
chmod 755                                       $1/trupax

echo INSTALLED.
