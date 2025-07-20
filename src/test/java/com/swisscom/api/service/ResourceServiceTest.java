package com.swisscom.api.service;

import com.swisscom.api.model.Resource;
import com.swisscom.api.dao.ResourceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class ResourceServiceTest {

    @Mock
    private ResourceRepository resourceRepository;

    @InjectMocks
    private ResourceService resourceService;

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
    void getAll_shouldReturnAllResources() {
        List<Resource> resources = Arrays.asList(resource);
        when(resourceRepository.findAll()).thenReturn(resources);

        List<Resource> result = resourceService.getAll();

        assertThat(result).hasSize(1);
        assertThat(result.get(0)).isEqualTo(resource);
        verify(resourceRepository).findAll();
    }

    @Test
    void getById_shouldReturnResource_whenExists() {
        when(resourceRepository.findById("test-id")).thenReturn(Optional.of(resource));

        Optional<Resource> result = resourceService.getByResourceId("test-id");

        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(resource);
        verify(resourceRepository).findById("test-id");
    }

    @Test
    void getById_shouldReturnEmpty_whenNotExists() {
        when(resourceRepository.findById("test-id")).thenReturn(Optional.empty());

        Optional<Resource> result = resourceService.getByResourceId("test-id");

        assertThat(result).isEmpty();
        verify(resourceRepository).findById("test-id");
    }

    @Test
    void save_shouldSaveAndReturnResource() {
        when(resourceRepository.save(resource)).thenReturn(resource);

        Resource savedResource = resourceService.save(resource);

        assertThat(savedResource).isEqualTo(resource);
        verify(resourceRepository).save(resource);
    }

    @Test
    void getByServiceId_shouldReturnResources() {
        List<Resource> resources = Arrays.asList(resource);
        when(resourceRepository.findByServiceId("service-1")).thenReturn(resources);

        List<Resource> result = resourceService.getByServiceId("service-1");

        assertThat(result).hasSize(1);
        assertThat(result.get(0)).isEqualTo(resource);
        verify(resourceRepository).findByServiceId("service-1");
    }

    @Test
    void delete_whenExists_shouldReturnTrue() {
        when(resourceRepository.existsById("test-id")).thenReturn(true);

        boolean result = resourceService.delete("test-id");

        assertThat(result).isTrue();
        verify(resourceRepository).deleteById("test-id");
        verify(resourceRepository).existsById("test-id");
    }

    @Test
    void delete_whenNotExists_shouldReturnFalse() {
        when(resourceRepository.existsById("test-id")).thenReturn(false);

        boolean result = resourceService.delete("test-id");

        assertThat(result).isFalse();
        verify(resourceRepository).existsById("test-id");
        verify(resourceRepository, never()).deleteById(any());
    }
}