package com.swisscom.api.controller;

import com.swisscom.api.model.Resource;
import com.swisscom.api.service.OwnerService;
import com.swisscom.api.service.ResourceService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class ResourceControllerTest {

    @Mock
    private ResourceService resourceService;
    @Mock
    private OwnerService ownerService;

    @InjectMocks
    private ResourceController controller;

    private Resource resource;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        resource = Resource.builder()
                .id("test-id")
                .serviceId("service-1")
                .build();
    }

    @Test
    void create_shouldReturnCreatedResource() {
        when(resourceService.save(any(Resource.class))).thenReturn(resource);

        ResponseEntity<Resource> response = controller.create(resource);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isEqualTo(resource);
        assertThat(response.getHeaders().getLocation().toString())
                .isEqualTo("/api/v1/resource/test-id");
        verify(resourceService).save(resource);
    }

    @Test
    void getByServiceId_shouldReturnResources() {
        List<Resource> resources = Arrays.asList(resource);
        when(resourceService.getByServiceId("service-1")).thenReturn(resources);

        ResponseEntity<List<Resource>> response = controller.getByService("service-1");

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).hasSize(1);
        assertThat(response.getBody().get(0)).isEqualTo(resource);
        verify(resourceService).getByServiceId("service-1");
    }

    @Test
    void getByServiceIdPaginated_shouldReturnPagedResources() {
        List<Resource> resources = Arrays.asList(resource);
        Page<Resource> page = new PageImpl<>(resources);
        when(resourceService.getByServiceIdPaginated(eq("service-1"), any(PageRequest.class)))
                .thenReturn(page);

        ResponseEntity<Page<Resource>> response = controller.getByServiceIdPaginated("service-1", 0, 10);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getContent()).hasSize(1);
        assertThat(response.getBody().getContent().get(0)).isEqualTo(resource);
        verify(resourceService).getByServiceIdPaginated(eq("service-1"), any(PageRequest.class));
    }

}