package de.nicolasschlecker.vv.smarthomeservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.nicolasschlecker.vv.smarthomeservice.domain.aktor.AktorRequestDto;
import de.nicolasschlecker.vv.smarthomeservice.repositories.AktorRepository;
import de.nicolasschlecker.vv.smarthomeservice.services.AktorService;
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
class AktorsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AktorRepository repository;

    @Autowired
    private AktorService service;

    @Autowired
    private ObjectMapper mapper;

    private AktorRequestDto createAktorRequestDto(Long id) {
        return new AktorRequestDto(id, "Test", "Test", "Test");
    }

    @Test
    void findAll() throws Exception {
        service.create(createAktorRequestDto(1L));
        service.create(createAktorRequestDto(2L));
        service.create(createAktorRequestDto(3L));

        mockMvc.perform(get("/api/v1/aktors"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(3)));
    }

    @Test
    void addAktor_idExists_alreadyReported() throws Exception {
        service.create(createAktorRequestDto(1L));

        final var json = mapper.writeValueAsString(createAktorRequestDto(1L));
        mockMvc.perform(
                post("/api/v1/aktors")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isAlreadyReported());
    }

    @Test
    void addAktor_invalidData_badRequest() throws Exception {
        final var json = mapper.writeValueAsString(new AktorRequestDto());
        mockMvc.perform(
                post("/api/v1/aktors")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isBadRequest());
    }

    @Test
    void addAktor_validData_ok() throws Exception {
        final var json = mapper.writeValueAsString(createAktorRequestDto(1L));
        mockMvc.perform(
                post("/api/v1/aktors")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk());
    }

    @Test
    void findAktorById_exists_ok() throws Exception {
        service.create(createAktorRequestDto(1L));

        mockMvc.perform(
                get("/api/v1/aktors/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void findAktorById_doesntExist_notFound() throws Exception {
        mockMvc.perform(
                get("/api/v1/aktors/1"))
                .andExpect(status().isNotFound());
    }
}
