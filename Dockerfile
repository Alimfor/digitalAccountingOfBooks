FROM openjdk:17-jdk

WORKDIR /app

COPY /target/spring-0.0.1-SNAPSHOT.jar /app/spring-0.0.1-SNAPSHOT.jar

CMD ["java", "-jar", "spring-0.0.1-SNAPSHOT.jar"]