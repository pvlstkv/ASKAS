FROM maven:3.8.1-jdk-11 AS build
COPY src /home/app/src
COPY pom.xml /home/app
CMD rm /home/app/src/main/resources/application.properties
COPY deployments/application.properties /home/app/src/main/resources
RUN mvn -f /home/app/pom.xml clean package -DskipTests

#
# Package stage
#
FROM openjdk:11-jdk
CMD ls /home/app/targetdock
COPY --from=build /home/app/target/javaserver-0.0.1-SNAPSHOT.jar /usr/local/lib/jserver.jar
ENTRYPOINT ["java","-jar", "-Dserver.port=8080", "/usr/local/lib/jserver.jar"]