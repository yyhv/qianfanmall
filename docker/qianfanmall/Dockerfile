FROM openjdk:8-jre
COPY application.yml application.yml
COPY qianfanmall.jar qianfanmall.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","qianfanmall.jar"]