package com.swisscom.api.controller;

import com.swisscom.api.model.Owner;
import com.swisscom.api.service.OwnerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class OwnerControllerTest {

    @Mock
    private OwnerService ownerService;

    @InjectMocks
    private OwnerController controller;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createOwner_shouldReturnCreatedOwner() {
        Owner owner = new Owner();
        owner.setId("test-id");
        when(ownerService.save(any(Owner.class))).thenReturn(owner);

        ResponseEntity<Owner> response = controller.createOwner(owner);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isEqualTo(owner);
        assertThat(response.getHeaders().getLocation().toString())
                .isEqualTo("/api/v1/owner/test-id");
    }

    @Test
    void getOwnersByServiceId_shouldReturnOwners() {
        List<Owner> owners = Arrays.asList(new Owner(), new Owner());
        when(ownerService.getByServiceId("service-id")).thenReturn(owners);

        ResponseEntity<List<Owner>> response = controller.getOwnersByServiceId("service-id");

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).hasSize(2);
    }

    @Test
    void getOwnersByResourceId_shouldReturnOwners() {
        List<Owner> owners = Arrays.asList(new Owner(), new Owner());
        when(ownerService.getByResourceId("resource-id")).thenReturn(owners);

        ResponseEntity<List<Owner>> response = controller.getOwnersByResourceId("resource-id");

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).hasSize(2);
    }

    @Test
    void updateOwner_shouldReturnUpdatedOwner() {
        Owner owner = new Owner();
        owner.setId("test-id");
        when(ownerService.save(any(Owner.class))).thenReturn(owner);

        ResponseEntity<Owner> response = controller.updateOwner(owner);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(owner);
    }

    @Test
    void deleteOwner_whenExists_shouldReturnNoContent() {
        when(ownerService.delete("test-id")).thenReturn(true);

        ResponseEntity<Void> response = controller.deleteOwner("test-id");

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }

    @Test
    void deleteOwner_whenNotExists_shouldReturnNotFound() {
        when(ownerService.delete("test-id")).thenReturn(false);

        ResponseEntity<Void> response = controller.deleteOwner("test-id");

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }
}