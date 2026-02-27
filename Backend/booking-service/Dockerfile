# Stage 1: Build the application
#Start with a maven image that includes JDK 17
FROM maven:3.9-amazoncorretto-17 AS build

#copy source code and pom.xml file to /app folder
WORKDIR /app
COPY pom.xml .
COPY src ./src
# Skip tests during build
RUN mvn clean package -DskipTests

# Stage 2: Create image & Run the application
FROM amazoncorretto:17.0.17
#set working folder to App and copt complied file from above step
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
#command to run the application
ENTRYPOINT ["java", "-jar", "app.jar"]