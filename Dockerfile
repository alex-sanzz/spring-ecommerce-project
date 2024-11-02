FROM openjdk:21-jdk-slim

WORKDIR /app

COPY .mvn/ .mvn/

COPY mvnw pom.xml ./

COPY src src/

RUN ./mvnw package -DskipTests

RUN cp target/*.jar app.jar

EXPOSE 8080

CMD ["java", "-jar", "app.jar"]