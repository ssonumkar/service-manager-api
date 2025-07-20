package com.swisscom.api.controller;

import com.swisscom.api.model.Resource;
import com.swisscom.api.service.OwnerService;
import com.swisscom.api.service.ResourceService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ResourceControllerTest {

    @Mock
    private ResourceService resourceService;
    @Mock
    private OwnerService ownerService;

    @InjectMocks
    private ResourceController controller;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void create_shouldReturnCreatedResource() {
        Resource resource = Resource.builder().id("test-id").build();
        when(resourceService.save(any(Resource.class))).thenReturn(resource);

        ResponseEntity<Resource> response = controller.create(resource);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isEqualTo(resource);
        assertThat(response.getHeaders().getLocation().toString())
                .isEqualTo("/api/v1/resource/test-id");
    }

    @Test
    void getAll_shouldReturnAllResources() {
        List<Resource> resources = Arrays.asList(
                Resource.builder().id("1").build(),
                Resource.builder().id("2").build()
        );
        when(resourceService.getAll()).thenReturn(resources);

        ResponseEntity<List<Resource>> response = controller.getAll();

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).hasSize(2);
    }

    @Test
    void getByService_shouldReturnResourcesForService() {
        List<Resource> resources = Collections.singletonList(
                Resource.builder().id("1").serviceId("service-1").build()
        );
        when(resourceService.getByServiceId("service-1")).thenReturn(resources);

        ResponseEntity<List<Resource>> response = controller.getByService("service-1");

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).hasSize(1);
    }

    @Test
    void delete_whenExists_shouldReturnNoContent() {
        when(resourceService.delete("test-id")).thenReturn(true);
        when(ownerService.getByResourceId("test-id")).thenReturn(Collections.emptyList());

        ResponseEntity<Void> response = controller.delete("test-id");

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }

    @Test
    void delete_whenNotExists_shouldReturnNotFound() {
        when(resourceService.delete("test-id")).thenReturn(false);
        when(ownerService.getByResourceId("test-id")).thenReturn(Collections.emptyList());

        ResponseEntity<Void> response = controller.delete("test-id");

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }
}