FROM adoptopenjdk/openjdk11:jdk-11.0.2.9-slim
WORKDIR /opt
ENV PORT 9090
EXPOSE 9090
COPY target/spring-boot-with-docker-kubernetes-1.0.jar /opt/application.jar
ENTRYPOINT exec java $JAVA_OPTS -jar application.jar