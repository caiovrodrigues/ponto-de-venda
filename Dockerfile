FROM maven:eclipse-temurin as build

WORKDIR /app

COPY ./pom.xml .

RUN mvn dependency:resolve

COPY ./src ./src

RUN mvn clean install -Dskiptests


FROM eclipse-temurin:21.0.3_9-jre-alpine

COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080

CMD ["java", "-jar", "app.jar"]