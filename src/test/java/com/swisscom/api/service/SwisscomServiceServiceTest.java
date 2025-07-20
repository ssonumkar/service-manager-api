package com.swisscom.api.service;

import com.swisscom.api.model.SwisscomService;
import com.swisscom.api.dao.SwisscomServiceRepository;

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

class SwisscomServiceServiceTest {

    @Mock
    private SwisscomServiceRepository serviceRepository;

    @InjectMocks
    private SwisscomServiceService serviceService;

    private SwisscomService service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        service = new SwisscomService();
        service.setId("test-id");
    }

    @Test
    void getAll_shouldReturnAllServices() {
        List<SwisscomService> services = Arrays.asList(service);
        when(serviceRepository.findAll()).thenReturn(services);

        List<SwisscomService> result = serviceService.getAll();

        assertThat(result).hasSize(1);
        assertThat(result.get(0)).isEqualTo(service);
        verify(serviceRepository).findAll();
    }

    @Test
    void getById_shouldReturnService_whenExists() {
        when(serviceRepository.findById("test-id")).thenReturn(Optional.of(service));

        Optional<SwisscomService> result = serviceService.getById("test-id");

        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(service);
        verify(serviceRepository).findById("test-id");
    }

    @Test
    void getById_shouldReturnEmpty_whenNotExists() {
        when(serviceRepository.findById("test-id")).thenReturn(Optional.empty());

        Optional<SwisscomService> result = serviceService.getById("test-id");

        assertThat(result).isEmpty();
        verify(serviceRepository).findById("test-id");
    }

    @Test
    void save_shouldSaveAndReturnService() {
        when(serviceRepository.save(service)).thenReturn(service);

        SwisscomService savedService = serviceService.save(service);

        assertThat(savedService).isEqualTo(service);
        verify(serviceRepository).save(service);
    }

    @Test
    void delete_whenExists_shouldReturnTrue() {
        when(serviceRepository.existsById("test-id")).thenReturn(true);

        boolean result = serviceService.delete("test-id");

        assertThat(result).isTrue();
        verify(serviceRepository).deleteById("test-id");
        verify(serviceRepository).existsById("test-id");
    }

    @Test
    void delete_whenNotExists_shouldReturnFalse() {
        when(serviceRepository.existsById("test-id")).thenReturn(false);

        boolean result = serviceService.delete("test-id");

        assertThat(result).isFalse();
        verify(serviceRepository).existsById("test-id");
        verify(serviceRepository, never()).deleteById(any());
    }
}