FROM inf-docker.fh-rosenheim.de/studwinten4338/docker-images/adoptopenjdk:11-jre-hotspot
RUN mkdir /opt/app
COPY ./build/libs/*.jar /opt/app
EXPOSE 8080
CMD ["java", "-jar", "/opt/app/vv-smarthomeservice-0.0.1-SNAPSHOT.jar"]