FROM openjdk:8-jdk-alpine
COPY ./target/productAPI-0.0.1-SNAPSHOT.jar productAPI-0.0.1-SNAPSHOT.jar
#ENTRYPOINT ["java","-jar", "shoppingCart-0.0.1-SNAPSHOT.jar"]
ENTRYPOINT ["java","-jar","-Dspring.profiles.active=dev", "productAPI-0.0.1-SNAPSHOT.jar"]