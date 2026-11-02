FROM amazoncorretto:17
COPY ./target/sem.jar /tmp
WORKDIR /tmp
ENTRYPOINT ["java", "-jar", "sem.jar", "db:3306", "30000"]