FROM eclipse-temurin:17-jre

WORKDIR /app

# 复制配置文件和 jar 包
COPY application.yml application.yml
COPY qianfanmall.jar qianfanmall.jar

# 创建存储和日志目录
RUN mkdir -p /storage /logs

# 暴露端口
EXPOSE 8080

# 启动应用，支持通过环境变量覆盖配置
ENTRYPOINT ["java", \
    "-Djava.security.egd=file:/dev/./urandom", \
    "-jar", "qianfanmall.jar"]