package de.nicolasschlecker.vv.smarthomeservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.nicolasschlecker.vv.smarthomeservice.domain.sensor.SensorRequestDto;
import de.nicolasschlecker.vv.smarthomeservice.repositories.SensorRepository;
import de.nicolasschlecker.vv.smarthomeservice.services.SensorService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
class SensorsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private SensorRepository repository;

    @Autowired
    private SensorService service;

    @Autowired
    private ObjectMapper mapper;

    private SensorRequestDto createSensorRequestDto(Long id) {
        return new SensorRequestDto(id, "Test", "Test");
    }

    @Test
    void findAll() throws Exception {
        service.create(createSensorRequestDto(1L));
        service.create(createSensorRequestDto(2L));
        service.create(createSensorRequestDto(3L));

        mockMvc.perform(get("/api/v1/sensors"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(3)));
    }

    @Test
    void addSensor_idExists_alreadyReported() throws Exception {
        service.create(createSensorRequestDto(1L));

        final var json = mapper.writeValueAsString(createSensorRequestDto(1L));
        mockMvc.perform(
                post("/api/v1/sensors")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isAlreadyReported());
    }

    @Test
    void addSensor_invalidData_badRequest() throws Exception {
        final var json = mapper.writeValueAsString(new SensorRequestDto());
        mockMvc.perform(
                post("/api/v1/sensors")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isBadRequest());
    }

    @Test
    void addSensor_validData_ok() throws Exception {
        final var json = mapper.writeValueAsString(createSensorRequestDto(1L));
        mockMvc.perform(
                post("/api/v1/sensors")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk());
    }

    @Test
    void findSensorById_exists_ok() throws Exception {
        service.create(createSensorRequestDto(1L));

        mockMvc.perform(
                get("/api/v1/sensors/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void findSensorById_doesntExist_notFound() throws Exception {
        mockMvc.perform(
                get("/api/v1/sensors/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void findSensorDataBySensorId_exists_ok() throws Exception {
        service.create(createSensorRequestDto(1L));

        mockMvc.perform(
                get("/api/v1/sensors/1/data"))
                .andExpect(status().isOk());
    }

    @Test
    void findSensorDataBySensorId_doesntExist_notFound() throws Exception {
        mockMvc.perform(
                get("/api/v1/sensors/1/data"))
                .andExpect(status().isNotFound());
    }

    @Test
    void updateSensor_idMismatch_badRequest() throws Exception {
        service.create(createSensorRequestDto(1L));

        final var sensorPartial = createSensorRequestDto(1L);
        final var json = mapper.writeValueAsString(sensorPartial);

        mockMvc.perform(
                put("/api/v1/sensors/2")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isBadRequest());
    }

    @Test
    void updateSensor_exists_ok() throws Exception {
        service.create(createSensorRequestDto(1L));

        final var sensorPartial = createSensorRequestDto(1L);
        final var json = mapper.writeValueAsString(sensorPartial);

        mockMvc.perform(
                put("/api/v1/sensors/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk());
    }

    @Test
    void deleteSensorById_doesntExists_notFound() throws Exception {
        mockMvc.perform(
                delete("/api/v1/sensors/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteSensorById_exists_noContent() throws Exception {
        service.create(createSensorRequestDto(1L));

        mockMvc.perform(
                delete("/api/v1/sensors/1"))
                .andExpect(status().isNoContent());
    }
}
