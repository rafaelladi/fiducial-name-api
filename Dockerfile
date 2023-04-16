FROM openjdk:17-alpine
COPY target/fiducial-1.0.0.jar fiducial-1.0.0.jar
ENTRYPOINT ["java", "-jar", "/fiducial-1.0.0.jar"]