FROM inf-docker.fh-rosenheim.de/studwinten4338/docker-images/adoptopenjdk:11-jre-hotspot
RUN mkdir /opt/app
COPY ./build/libs/*.jar /opt/app
EXPOSE 3000
CMD ["java", "-jar", "/opt/app/vv-project-1.0-SNAPSHOT.jar"]
