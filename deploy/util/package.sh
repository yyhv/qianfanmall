#!/bin/bash

# 请注意
# 本脚本的作用是把本项目编译的结果保存到deploy文件夹中
# 1. 把项目数据库文件拷贝到deploy/db
# 2. 编译qianfanmall-admin
# 3. 编译qianfanmall-all模块，然后拷贝到deploy/qianfanmall

DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" >/dev/null && pwd )"
cd $DIR/../..
LITEMALL_HOME=$PWD
echo "LITEMALL_HOME $LITEMALL_HOME"

# 复制数据库
cat $LITEMALL_HOME/qianfanmall-db/sql/qianfanmall_schema.sql > $LITEMALL_HOME/deploy/db/qianfanmall.sql
cat $LITEMALL_HOME/qianfanmall-db/sql/qianfanmall_table.sql >> $LITEMALL_HOME/deploy/db/qianfanmall.sql
cat $LITEMALL_HOME/qianfanmall-db/sql/qianfanmall_data.sql >> $LITEMALL_HOME/deploy/db/qianfanmall.sql

# 打包qianfanmall-admin
cd $LITEMALL_HOME/qianfanmall-admin
npm install --registry=https://registry.npm.taobao.org
npm run build:dep

# 打包qianfanmall-vue
cd $LITEMALL_HOME/qianfanmall-vue
npm install --registry=https://registry.npm.taobao.org
npm run build:dep

cd $LITEMALL_HOME
mvn clean package
cp -f $LITEMALL_HOME/qianfanmall-all/target/qianfanmall-all-*-exec.jar $LITEMALL_HOME/deploy/qianfanmall/qianfanmall.jar