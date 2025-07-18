package com.swisscom.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.swisscom.api.model.Owner;
import com.swisscom.api.model.Resource;
import com.swisscom.api.model.SwisscomService;
import com.swisscom.api.service.SwisscomApiServiceManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class ServiceManagerApiControllerTest {

    @Autowired
    private MockMvc mockMvc;


    private SwisscomApiServiceManager serviceManager = Mockito.mock(SwisscomApiServiceManager.class);

    @Autowired
    private ObjectMapper objectMapper;

    private SwisscomService testService;
    private String expectedJson;

    @BeforeEach
    void setUp() throws IOException {
        Owner owner1 = new Owner("owner_id_1_1", "Owner 1", "AC001", 1);
        Owner owner2 = new Owner("owner_id_1_2", "Owner 2", "AC002", 2);
        Resource resource1 = new Resource("resource_id_1", List.of(owner1, owner2));

        Owner owner3 = new Owner("owner_id_2_1", "Owner 3", "AC003", 1);
        Owner owner4 = new Owner("owner_id_2_2", "Owner 4", "AC004", 2);
        Resource resource2 = new Resource("resource_id_2", List.of(owner3, owner4));

        testService = new SwisscomService("test-id", List.of(resource1, resource2));

        expectedJson = Files.readString(Paths.get("src/test/resources/json/expected-service.json"));

    }

    @Test
    void testCreateService_shouldReturn201_andMatchJson() throws Exception {
        when(serviceManager.save(any(SwisscomService.class))).thenReturn(testService);

        this.mockMvc.perform(post("/api/services")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testService)))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "/api/services/test-id"))
                .andExpect(content().json(expectedJson, true));
    }

    @Test
    void testGetService_shouldReturn200_andMatchJson() throws Exception {
        when(serviceManager.get("test-id")).thenReturn(Optional.of(testService));

        mockMvc.perform(get("/api/services/test-id"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(expectedJson, true));
    }

    @Test
    void testGetService_shouldReturn404_whenNotFound() throws Exception {
        when(serviceManager.get("not-found")).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/services/not-found"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testUpdateService_shouldReturn200_andMatchJson() throws Exception {
        when(serviceManager.get("test-id")).thenReturn(Optional.of(testService));
        when(serviceManager.update(any(SwisscomService.class))).thenReturn(testService);

        mockMvc.perform(put("/api/services/test-id")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testService)))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedJson, true));
    }

    @Test
    void testDeleteService_shouldReturn204_whenFound() throws Exception {
        when(serviceManager.delete("test-id")).thenReturn(true);

        mockMvc.perform(delete("/api/services/test-id"))
                .andExpect(status().isNoContent());
    }

    @Test
    void testDeleteService_shouldReturn404_whenNotFound() throws Exception {
        when(serviceManager.delete("not-found")).thenReturn(false);

        mockMvc.perform(delete("/api/services/not-found"))
                .andExpect(status().isNotFound());
    }
}
