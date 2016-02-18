#!/bin/bash
cd `dirname $0`
BIN_DIR=`pwd`
cd ..
DEPLOY_DIR=`pwd`
SERVICE_NAME=`basename $DEPLOY_DIR`