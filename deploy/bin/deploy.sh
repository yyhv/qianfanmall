#!/bin/bash

# 本脚本的作用是停止当前Spring Boot应用，然后再次部署
PID=$(ps -ef | grep qianfanmall.jar | grep -v grep | awk '{ print $2 }')

if [ ! -z "$PID" ]
then
    kill $PID
fi

cd /home/ubuntu/deploy/qianfanmall || exit 2
nohup java -jar qianfanmall.jar > log.log 2>&1 &