# Distributed Systems Student Project

This is a student project I did in my fourth semester of computer science for the Distributed Systems course at the [Rosenheim Technical University of Applied Sciencess](https://th-rosenheim.de).

## Project structure

### General

- **application:** Application logic as well as external dependencies
- **common:** Central place for common logic used throughout the application.
- **domain:** Models
- **controller:** Rest controller
- **repositories:** Repositories that communicate with the database
- **service:** Services to translate between controllers and repositories

### Submodules

The structure above applies to all of these submodules.

- sensor
- aktorDto
- smart-home-service

## Environment variables

The environment variables for the docker-compose network are:

- **DB_PASSWORD:** Password to use for the postgres database
- **WEATHER_SERVICE_USERNAME:** Username for the weather service
- **WEATHER_SERVICE_PASSWORD:** Password for the weather service

## Getting started

### Configuration

Copy the `template.env`, name it `.env` and configure the environment variables described above.

### Running docker compose

[Docker](https://www.docker.com/) has to be installed on your machine.

#### From IntelliJ

In the root project run the `docker` -> `composeUp` task.

This will build all three sub-projects, generate their respective `Docker` files and then run `docker-comopse`.

#### From the CLI

Windows

```bash
gradlew.bat composeUp
```

MacOS / Linux

```bash
./gradlew composeUp
```

## License

[MIT](https://choosealicense.com/licenses/mit/)