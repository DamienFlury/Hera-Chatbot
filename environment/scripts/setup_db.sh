#!/bin/bash

start=$PWD
script=`dirname "$BASH_SOURCE"`
cd $script
cd ../sql

commands=$(mktemp)
trap "rm -f $commands" 0 2 3 15

echo "DROP DATABASE IF EXISTS $HERA_DB_NAME;" >> $commands
echo "CREATE DATABASE $HERA_DB_NAME;" >> $commands
echo "USE $HERA_DB_NAME;" >> $commands
cat tables/* constraints.sql >> $commands

mysql -u $HERA_DB_USER -p$HERA_DB_PWD < $commands

cd $start