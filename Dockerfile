FROM openjdk:11
ADD target/user-authentication-1.0.0.jar user-authentication-1.0.0.jar
ENTRYPOINT ["java","-jar","/user-authentication-1.0.0.jar"]