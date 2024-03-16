# Build Stage
FROM maven:3.8.4-openjdk-17-slim AS build 
WORKDIR /app
COPY swd /app/swd
RUN mvn package -f /app/swd/pom.xml

# Final Stage
FROM openjdk:17-slim
WORKDIR /app
COPY --from=build /app/swd/target/SWD392-0.0.1-SNAPSHOT.war app.war

EXPOSE 8080
CMD ["java","-jar","app.war"]
