package de.nicolasschlecker.vv.smarthomeservice.domain.aktor;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum ShutterStatus {
    @JsonProperty("Open")
    OPEN,
    @JsonProperty("Closed")
    CLOSED
}
