package com.swisscom.api.controller;

import com.swisscom.api.dao.ResourceRepository;
import com.swisscom.api.model.Resource;
import com.swisscom.api.model.ResourceSummaryDTO;
import com.swisscom.api.service.OwnerService;
import com.swisscom.api.service.ResourceService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/v1/resource")
@RequiredArgsConstructor
public class ResourceController {
    private final ResourceService service;
    private final OwnerService ownerService;

    @PostMapping
    public ResponseEntity<Resource> create(@Valid @RequestBody Resource r) {
        return ResponseEntity.created(URI.create("/api/v1/resource/" + r.getId()))
                .body(service.save(r));
    }

    @GetMapping
    public ResponseEntity<List<Resource>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/by-service/{serviceId}")
    public ResponseEntity<List<Resource>> getByService(@PathVariable String serviceId) {
        return ResponseEntity.ok(service.getByServiceId(serviceId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        ownerService.getByResourceId(id).forEach(owner -> {
            ownerService.delete(owner.getId());
        });
        return service.delete(id) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}