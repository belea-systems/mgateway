FROM sapmachine:25-jre-alpine-3.23

WORKDIR /app

COPY build/libs/mgateway-*.jar app.jar

ENTRYPOINT [ "java", "-jar", "app.jar" ]