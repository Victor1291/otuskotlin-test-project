FROM bellsoft/liberica-openjdk-alpine:17
COPY build/libs/ok-menu-app-spring/build/libs/ok-menu-app-spring-0.0.1.jar /app/app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-XX:+UseContainerSupport", "-jar", "/app/app.jar"]
