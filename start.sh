#!/bin/bash
PID_FILE=lock/pid
OLD_PATH=`pwd`
FILE_PATH=`dirname $0`

cd $FILE_PATH
mkdir -p lock

CP=.
for i in `ls lib/*.jar`; do CP=$CP:$i; done
rm -f ./nohup.out;

# the command to start nsi-ri
NSIRI="java -classpath lib/*.jar:lib/* net.geant.nsicontest.main.NsiApp"

if [ -e $PID_FILE ] ; then
  pid=`cat $PID_FILE`
  name=`ps -p $pid -o comm= 2>/dev/null`

  if [ $name ] && [ $name == "java" ] ; then
    echo "seems that NSI-RI is already running"
    exit
  else
    rm $PID_FILE
  fi
fi

echo "Starting NSI-RI daemon"
touch ./nohup.out
nohup $NSIRI &
pid=$!
echo $pid >$PID_FILE
cd $OLD_PATH
tail -f $FILE_PATH/nohup.out
