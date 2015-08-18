#!/bin/bash

SCRIPT=$(readlink -f "$0")
SCRIPT_PATH=$(dirname "$SCRIPT")
if [[ $SCRIPT_PATH =~ ^/ ]]; then
        BASEDIR=$SCRIPT_PATH
else
        cur_dir=$(pwd)
        BASEDIR=$cur_dir/$SCRIPT_PATH
fi

cd $BASEDIR
if [ -f "$1/sample.xml" ]; then
        java -cp $BASEDIR:$BASEDIR/bin:. -Xms2048m -Xmx2048m Phase1 $1
else
        echo "sample.xml file not exists at [$1]"
fi
