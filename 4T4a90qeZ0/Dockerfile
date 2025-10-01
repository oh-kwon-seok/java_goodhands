# 사용할 베이스 이미지
FROM azul/zulu-openjdk:17


ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} app.jar

ENV SPRING_PROFILES_ACTIVE=prod
ENV JAVA_TOOL_OPTIONS="-Djava.net.preferIPv4Stack=true -Djava.net.preferIPv4Addresses=true"



ENTRYPOINT ["java","-jar","/app.jar"]
