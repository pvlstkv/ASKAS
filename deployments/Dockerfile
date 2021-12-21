FROM maven:3.8.4-jdk-11 as build
COPY src /home/app/src
COPY pom.xml /home/app
CMD rm /home/app/src/main/resources/application.properties
COPY deployments/application.properties /home/app/src/main/resources
RUN mvn -f /home/app/pom.xml clean install package -DskipTests


#WORKDIR /home/app
FROM openjdk:11-jdk
CMD ls /home/app/targetdock
COPY --from=build /home/app/target/*.jar /usr/local/lib/jserver.jar
ENTRYPOINT ["java","-jar", "-Dserver.port=8080", "/usr/local/lib/jserver.jar"]
