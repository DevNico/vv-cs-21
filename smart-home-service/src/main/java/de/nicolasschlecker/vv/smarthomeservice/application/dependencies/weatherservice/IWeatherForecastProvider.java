package de.nicolasschlecker.vv.smarthomeservice.application.dependencies.weatherservice;

import java.util.Optional;

public interface IWeatherForecastProvider {
    Optional<String> getWeather();
}
