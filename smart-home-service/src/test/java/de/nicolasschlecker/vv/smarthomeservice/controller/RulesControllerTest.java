package de.nicolasschlecker.vv.smarthomeservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.nicolasschlecker.vv.smarthomeservice.domain.aktor.AktorRequestDto;
import de.nicolasschlecker.vv.smarthomeservice.domain.rule.RuleRequestDto;
import de.nicolasschlecker.vv.smarthomeservice.domain.sensor.SensorRequestDto;
import de.nicolasschlecker.vv.smarthomeservice.services.AktorService;
import de.nicolasschlecker.vv.smarthomeservice.services.RuleService;
import de.nicolasschlecker.vv.smarthomeservice.services.SensorService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
class RulesControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private RuleService service;

    @Autowired
    private AktorService aktorService;

    @Autowired
    private SensorService sensorService;

    @Autowired
    private ObjectMapper mapper;

    private RuleRequestDto createRuleRequestDto(String name) {
        final var aktorId = System.nanoTime();
        final var sensorId = System.nanoTime();
        aktorService.create(new AktorRequestDto(aktorId, "T", "T", "T"));
        sensorService.create(new SensorRequestDto(sensorId, "T", "T"));
        return new RuleRequestDto(name, 10, sensorId, aktorId);
    }

    @Test
    void findAll() throws Exception {
        service.create(createRuleRequestDto("R1"));
        service.create(createRuleRequestDto("R2"));
        service.create(createRuleRequestDto("R3"));

        mockMvc.perform(get("/api/v1/rules"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(3)));
    }

    @Test
    void addRule_idExists_alreadyReported() throws Exception {
        service.create(createRuleRequestDto("R1"));

        final var json = mapper.writeValueAsString(createRuleRequestDto("R1"));
        mockMvc.perform(
                post("/api/v1/rules")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isAlreadyReported());
    }

    @Test
    void addRule_invalidData_badRequest() throws Exception {
        final var json = mapper.writeValueAsString(new RuleRequestDto());
        mockMvc.perform(
                post("/api/v1/rules")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isBadRequest());
    }

    @Test
    void addRule_validData_ok() throws Exception {
        final var json = mapper.writeValueAsString(createRuleRequestDto("R1"));
        mockMvc.perform(
                post("/api/v1/rules")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk());
    }

    @Test
    void findRuleById_exists_ok() throws Exception {
        final var rule = service.create(createRuleRequestDto("R1"));

        mockMvc.perform(
                get("/api/v1/rules/" + rule.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void findRuleById_doesntExist_notFound() throws Exception {
        mockMvc.perform(
                get("/api/v1/rules/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void findRuleByName_exists_ok() throws Exception {
        final var rule = service.create(createRuleRequestDto("R1"));

        mockMvc.perform(
                get("/api/v1/rules/" + rule.getName()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void findRuleByName_doesntExist_notFound() throws Exception {
        mockMvc.perform(
                get("/api/v1/rules/test"))
                .andExpect(status().isNotFound());
    }
}
