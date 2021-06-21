# VV Studienarbeit

[![pipeline status](https://inf-git.fh-rosenheim.de/vv-inf-sose21/schleckernicolas/badges/main/pipeline.svg)](https://inf-git.fh-rosenheim.de/vv-inf-sose21/schleckernicolas/-/commits/main)
[![coverage report](https://inf-git.fh-rosenheim.de/vv-inf-sose21/schleckernicolas/badges/main/coverage.svg)](https://inf-git.fh-rosenheim.de/vv-inf-sose21/schleckernicolas/-/commits/main)

## Project structure

**de.nicolasschlecker.vv**

- **common:** Central place for common logic used throughout the application. Mainly Logging and Serialisation.
- **domain:** Models and exceptions
- **net:** All network related code, mainly: MeasurementServer

The class `de.nicolasschlecker.vv.DemoSensor` contains and example Sensor that tries to connect to a local
MeasurementServer, performs the handshake and then sends 10 Measurements.

## Environment variables

- **PORT:** The port the server should use
- **LOG_FILE:** Path for the log file
- **LOG_LEVEL:** One of `[OFF, SEVERE, WARNING, INFO, CONFIG, FINE, FINER, FINEST, ALL]`
- **JSON_FILE:** Path for the file containing received measurements

## Building and running the project manually

Use the build tool [gradle](https://gradle.org/) to build this project.

```bash
./gradlew build
```

To start the server just run

```bash
java -jar build/libs/vv-project-1.0-SNAPSHOT.jar
```

## Build and running Docker image

For building the image [docker](https://www.docker.com/) has to be installed on your machine.

```bash
docker build -t station .
```

You can then run the image with

```bash
docker run -p 3000:3000 station:latest
```

If you want the server to run interactively (with console output) run

```bash
docker run -it -p 3000:3000 station:latest
```

## License

[MIT](https://choosealicense.com/licenses/mit/)