FROM openjdk:17-jdk
ADD build/libs/*SNAPSHOT.jar /app.jar
RUN bash -c 'touch /app.jar'
ENV USE_PROFILE=prod
ENV LOCATION "/"
ENTRYPOINT ["java", "-jar", "/app.jar", "--spring.config.location=${LOCATION}","--spring.profiles.active=${USE_PROFILE}"]
