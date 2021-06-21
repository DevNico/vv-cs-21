package de.nicolasschlecker.vv.aktor.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum ShutterStatus {
    @JsonProperty("Open")
    OPEN,
    @JsonProperty("Closed")
    CLOSED
}
