#!/bin/bash

PID=$(ps -ef | grep qianfanmall.jar | grep -v grep | awk '{ print $2 }')

if [ ! -z "$PID" ]
then
    kill $PID
fi