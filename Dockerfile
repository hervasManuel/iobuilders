FROM gradle:7.4.2-jdk17-jammy as build
# Only copy dependency-related files
COPY build.gradle settings.gradle /home/gradle/src/
WORKDIR /home/gradle/src
# Only download dependencies
RUN gradle --no-daemon clean build  > /dev/null 2>&1 || true
# Copy all files
COPY --chown=gradle:gradle . /home/gradle/src
# Do the actual build
RUN gradle --no-daemon build

FROM amazoncorretto:17.0.5-al2
WORKDIR /home/app
ENV TZ=Europe/Madrid
COPY --from=build /home/gradle/src/build/libs/bank-0.0.1-SNAPSHOT.jar /home/app/application.jar
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "/home/app/application.jar"]
