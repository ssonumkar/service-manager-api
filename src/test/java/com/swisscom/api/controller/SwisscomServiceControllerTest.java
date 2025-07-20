package com.swisscom.api.controller;

import com.swisscom.api.model.SwisscomService;
import com.swisscom.api.service.OwnerService;
import com.swisscom.api.service.ResourceService;
import com.swisscom.api.service.SwisscomServiceService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class SwisscomServiceControllerTest {

    @Mock
    private SwisscomServiceService serviceService;
    @Mock
    private ResourceService resourceService;
    @Mock
    private OwnerService ownerService;

    @InjectMocks
    private SwisscomServiceController controller;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void create_shouldReturnCreatedService() {
        SwisscomService service = new SwisscomService("test-id");
        when(serviceService.save(any(SwisscomService.class))).thenReturn(service);

        ResponseEntity<SwisscomService> response = controller.create(service);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isEqualTo(service);
        assertThat(response.getHeaders().getLocation().toString())
                .isEqualTo("/api/v1/service/test-id");
    }

    @Test
    void getAll_shouldReturnAllServices() {
        List<SwisscomService> services = Arrays.asList(
                new SwisscomService("1"),
                new SwisscomService("2")
        );
        when(serviceService.getAll()).thenReturn(services);

        ResponseEntity<List<SwisscomService>> response = controller.getAll();

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).hasSize(2);
    }

    @Test
    void getById_whenExists_shouldReturnService() {
        SwisscomService service = new SwisscomService("test-id");
        when(serviceService.getById("test-id")).thenReturn(Optional.of(service));

        ResponseEntity<SwisscomService> response = controller.getById("test-id");

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(service);
    }

    @Test
    void getById_whenNotExists_shouldReturnNotFound() {
        when(serviceService.getById("test-id")).thenReturn(Optional.empty());

        ResponseEntity<SwisscomService> response = controller.getById("test-id");

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void delete_whenExists_shouldReturnNoContent() {
        when(serviceService.delete("test-id")).thenReturn(true);

        ResponseEntity<Void> response = controller.delete("test-id");

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }

    @Test
    void delete_whenNotExists_shouldReturnNotFound() {
        when(serviceService.delete("test-id")).thenReturn(false);

        ResponseEntity<Void> response = controller.delete("test-id");

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }
}