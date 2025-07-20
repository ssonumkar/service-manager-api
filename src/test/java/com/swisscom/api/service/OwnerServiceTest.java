package com.swisscom.api.service;

import com.swisscom.api.model.Owner;
import com.swisscom.api.model.SwisscomService;
import com.swisscom.api.model.Resource;
import com.swisscom.api.dao.OwnerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

class OwnerServiceTest {

    @Mock
    private OwnerRepository ownerRepository;

    @Mock
    private ResourceService resourceService;

    @Mock
    private SwisscomServiceService swisscomService;

    @InjectMocks
    private OwnerService ownerService;

    private Owner owner;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        owner = Owner.builder()
                .id("test-id")
                .serviceId("service-1")
                .resourceId("resource-1")
                .name("Test Owner")
                .accountNumber("ACC123")
                .level(1)
                .build();
    }

    @Test
    void getAll_shouldReturnAllOwners() {
        List<Owner> owners = Arrays.asList(owner);
        when(ownerRepository.findAll()).thenReturn(owners);

        List<Owner> result = ownerService.getAll();

        assertThat(result).hasSize(1);
        assertThat(result.get(0)).isEqualTo(owner);
        verify(ownerRepository).findAll();
    }

    @Test
    void save_shouldValidateAndSaveOwner() {
        when(swisscomService.getById("service-1"))
                .thenReturn(Optional.of(new SwisscomService()));
        when(resourceService.getByResourceId("resource-1"))
                .thenReturn(Optional.of(new Resource()));
        when(ownerRepository.save(owner)).thenReturn(owner);

        Owner savedOwner = ownerService.save(owner);

        assertThat(savedOwner).isEqualTo(owner);
        verify(swisscomService).getById("service-1");
        verify(resourceService).getByResourceId("resource-1");
        verify(ownerRepository).save(owner);
    }

    @Test
    void save_shouldThrowException_whenServiceNotFound() {
        when(swisscomService.getById("service-1")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> ownerService.save(owner))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Invalid service ID: service-1");

        verify(ownerRepository, never()).save(any());
    }

    @Test
    void save_shouldThrowException_whenResourceNotFound() {
        when(swisscomService.getById("service-1"))
                .thenReturn(Optional.of(new SwisscomService()));
        when(resourceService.getByResourceId("resource-1")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> ownerService.save(owner))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Invalid resource ID: resource-1");

        verify(ownerRepository, never()).save(any());
    }

    @Test
    void saveAll_shouldSaveMultipleOwners() {
        List<Owner> owners = Arrays.asList(owner);
        when(ownerRepository.saveAll(owners)).thenReturn(owners);

        ownerService.saveAll(owners, "service-1");

        verify(ownerRepository).saveAll(owners);
    }

    @Test
    void getByServiceId_shouldReturnOwners() {
        List<Owner> owners = Arrays.asList(owner);
        when(ownerRepository.findByServiceId("service-1")).thenReturn(owners);

        List<Owner> result = ownerService.getByServiceId("service-1");

        assertThat(result).hasSize(1);
        assertThat(result.get(0)).isEqualTo(owner);
        verify(ownerRepository).findByServiceId("service-1");
    }

    @Test
    void getByResourceId_shouldReturnOwners() {
        List<Owner> owners = Arrays.asList(owner);
        when(ownerRepository.findByResourceId("resource-1")).thenReturn(owners);

        List<Owner> result = ownerService.getByResourceId("resource-1");

        assertThat(result).hasSize(1);
        assertThat(result.get(0)).isEqualTo(owner);
        verify(ownerRepository).findByResourceId("resource-1");
    }

    @Test
    void delete_whenExists_shouldReturnTrue() {
        when(ownerRepository.existsById("test-id")).thenReturn(true);

        boolean result = ownerService.delete("test-id");

        assertThat(result).isTrue();
        verify(ownerRepository).deleteById("test-id");
    }

    @Test
    void delete_whenNotExists_shouldReturnFalse() {
        when(ownerRepository.existsById("test-id")).thenReturn(false);

        boolean result = ownerService.delete("test-id");

        assertThat(result).isFalse();
        verify(ownerRepository, never()).deleteById(any());
    }
}