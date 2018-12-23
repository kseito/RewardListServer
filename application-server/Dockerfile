FROM openjdk:8-jdk-alpine

VOLUME /tmp

RUN mkdir /app
WORKDIR /app

#TODO ファイル名をENTRYPOINTする方法がわからない
ENV JAR_FILE "rewardlist-0.0.1-SNAPSHOT.jar"

ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","build/libs/rewardlist-0.0.1-SNAPSHOT.jar"]