package de.nicolasschlecker.vv.common.config;

import de.nicolasschlecker.vv.domain.exceptions.InvalidConfigurationException;
import de.nicolasschlecker.vv.domain.models.Config;

public interface IConfigLoader {
    Config getConfig(Config defaultConfig) throws InvalidConfigurationException;
}
