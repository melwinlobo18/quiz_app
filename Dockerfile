FROM maven:3.5-jdk-8-alpine
WORKDIR /app
COPY ./ /app
RUN mvn install

FROM openjdk:8-jre-alpine
COPY --from=0 /app/target/QuizZz-0.0.1-SNAPSHOT.jar /
CMD ["java -jar QuizZz-0.0.1-SNAPSHOT.jar"] 