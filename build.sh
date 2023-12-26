#!/bin/bash

set -e

rm -rf target

# comment the next line to run the full, long-running kind of tests
export DE_ORG_MCHAHN_BASELIB_TEST_QUICK=1

mvn package -Dmaven.test.skip=true
mvn install -Dmaven.test.skip=true
