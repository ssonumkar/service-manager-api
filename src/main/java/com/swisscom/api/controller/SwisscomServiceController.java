package com.swisscom.api.controller;

import com.swisscom.api.model.Owner;
import com.swisscom.api.model.RegisterFullServiceRequest;
import com.swisscom.api.model.Resource;
import com.swisscom.api.model.SwisscomService;
import com.swisscom.api.service.IOwnerService;
import com.swisscom.api.service.IResourceService;
import com.swisscom.api.service.ISwisscomServiceService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/service")
@AllArgsConstructor
public class SwisscomServiceController {

    private final ISwisscomServiceService service;
    private final IResourceService resourceService;
    private final IOwnerService ownerService;

    @PostMapping
    public ResponseEntity<SwisscomService> create(@Valid @RequestBody SwisscomService s) {
        return ResponseEntity.created(URI.create("/api/v1/service/" + s.getId()))
                .body(service.save(s));
    }

    @GetMapping("/paginated")
    public ResponseEntity<Page<SwisscomService>> getAllPaginated(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(service.getAllPaginated(PageRequest.of(page, size)));
    }

    @GetMapping
    public ResponseEntity<List<SwisscomService>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SwisscomService> getById(@PathVariable String id) {
        return service.getById(id).map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        return service.delete(id) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    @PostMapping("/registerFull")
    public ResponseEntity<String> registerFullService(@RequestBody RegisterFullServiceRequest dto) {
        log.info("Registering full service structure: {}", dto.getServiceId());

        // Save service
        SwisscomService swisscomService = new SwisscomService(dto.getServiceId());
        service.save(swisscomService);
        List<Resource> resourcesToSave = new ArrayList<>();
        List<Owner> ownersToSave = new ArrayList<>();

        // Save resources and owners
        if (dto.getResources() != null) {
            for (RegisterFullServiceRequest.ResourceDTO resDto : dto.getResources()) {
                Resource res = Resource.builder()
                        .id(resDto.getResourceId())
                        .serviceId(dto.getServiceId())
                        .build();
                resourcesToSave.add(res);

                if (resDto.getOwners() != null) {
                    for (Owner o : resDto.getOwners()) {
                        o.setServiceId(dto.getServiceId());
                        o.setResourceId(resDto.getResourceId());
                        ownersToSave.add(o);
                    }
                }
            }
        }
        // Save all resources and owners
        resourceService.saveAll(resourcesToSave);
        ownerService.saveAll(ownersToSave, dto.getServiceId());
        return ResponseEntity.created(URI.create("/api/v1/service/" + dto.getServiceId()))
                .body("Service registered successfully with ID: " + dto.getServiceId());
    }

}
