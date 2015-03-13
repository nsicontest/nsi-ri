#!/bin/sh
PID_FILE=lock/pid

if [ -e $PID_FILE ] ; then
  pid=`cat $PID_FILE`
else
  echo "seems that NSI-RI is not running"
  exit
fi

name=`ps -p $pid -o comm= 2>/dev/null`
if [ -z "$name" ] || [ $name != "java" ] ; then
  rm $PID_FILE
  echo "seems that NSI-RI is not running"
  exit
fi

echo "Terminating NSI-RI"
kill -15 $pid
rm $PID_FILE
echo "NSI-RI terminated"
