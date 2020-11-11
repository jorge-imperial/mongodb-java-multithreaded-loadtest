#!/bin/bash

PROCESSES=5
i=0

while [ $i -le $PROCESSES ] 
do
  # mvn exec:java -Dexec.mainClass="app.ApplicationMain" &>client-$i.log  &
  mvn exec:java -Dexec.mainClass=app.ApplicationMain  -Dexec.args="--maxThreads 300" &> client-$i.log & 
  i=$((i+1))
done

