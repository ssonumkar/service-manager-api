package com.swisscom.api.controller;

import com.swisscom.api.model.Resource;
import com.swisscom.api.service.IOwnerService;
import com.swisscom.api.service.IResourceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/resource")
@RequiredArgsConstructor
public class ResourceController {
    private final IResourceService service;
    private final IOwnerService ownerService;

    @PostMapping
    public ResponseEntity<Resource> create(@Valid @RequestBody Resource r) {
        return ResponseEntity.created(URI.create("/api/v1/resource/" + r.getId()))
                .body(service.save(r));
    }

    @GetMapping("/by-service/{serviceId}/paginated")
    public ResponseEntity<Page<Resource>> getByServiceIdPaginated(
            @PathVariable String serviceId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(service.getByServiceIdPaginated(serviceId, PageRequest.of(page, size)));
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