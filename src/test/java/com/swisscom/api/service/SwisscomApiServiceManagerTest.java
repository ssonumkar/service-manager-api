package com.swisscom.api.service;

import com.swisscom.api.dao.SwisscomServiceRepository;
import com.swisscom.api.model.Resource;
import com.swisscom.api.model.SwisscomService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SwisscomApiServiceManagerTest {
    @Mock
    private SwisscomServiceRepository swisscomServiceRepository;
    @InjectMocks
    private SwisscomApiServiceManager swisscomApiServiceManager;

    private SwisscomService swisscomService;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        swisscomService = new SwisscomService();
        swisscomService.setId("test_id_1");
        swisscomService.setResources(new ArrayList<Resource>());
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void post_shouldSaveAndReturnResource() {
        when(swisscomServiceRepository.save(swisscomService)).thenReturn(swisscomService);
        SwisscomService actualResult = swisscomServiceRepository.save(swisscomService);

        assertThat(actualResult).isEqualTo(swisscomService);
        verify(swisscomServiceRepository, times(1)).save(swisscomService);
    }

    @Test
    void get_shouldReturnOptionalResource_ifExists() {
        when(swisscomServiceRepository.findById(swisscomService.getId())).thenReturn(Optional.of(swisscomService));

        Optional<SwisscomService> actualResult = swisscomServiceRepository.findById(swisscomService.getId());

        assertThat(actualResult).isEqualTo(swisscomService);
        verify(swisscomServiceRepository, times(1)).findById(swisscomService.getId());
    }

    @Test
    void get_shouldReturnEmpty_ifNotFound(){
        when(swisscomServiceRepository.findById("xyz")).thenReturn(Optional.empty());

        Optional<SwisscomService> result = swisscomApiServiceManager.get("xyz");

        assertThat(result).isNull();
    }
    @Test
    void update_shouldUpdateResource_ifFound() {
        swisscomService.getResources().add(new Resource());
        when(swisscomServiceRepository.save(swisscomService)).thenReturn(swisscomService);

        SwisscomService actualResult = swisscomApiServiceManager.update(swisscomService);

        assertThat(actualResult).isEqualTo(swisscomService);
    }

    @Test
    void delete_shouldReturnTrue_ifExists() {
        when(swisscomServiceRepository.existsById("abc123")).thenReturn(true);

        boolean result = swisscomApiServiceManager.delete("abc123");

        assertThat(result).isTrue();
        verify(swisscomServiceRepository).deleteById("abc123");
    }

    @Test
    void delete_shouldReturnFalse_ifNotFound() {
        when(swisscomServiceRepository.existsById("xyz")).thenReturn(false);

        boolean result = swisscomApiServiceManager.delete("xyz");

        assertThat(result).isFalse();
        verify(swisscomServiceRepository, never()).deleteById(any());
    }
}