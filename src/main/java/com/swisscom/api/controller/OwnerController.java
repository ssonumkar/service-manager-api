package com.swisscom.api.controller;

import com.swisscom.api.model.Owner;
import com.swisscom.api.service.IOwnerService;
import com.swisscom.api.service.OwnerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.net.URI;
import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/v1/owner")
@AllArgsConstructor
@Tag(name = "Owner Management", description = "Operations related to owners of services and resources")
public class OwnerController {
    private final IOwnerService ownerService;
    @PostMapping
    public ResponseEntity<Owner> createOwner(@RequestBody Owner owner) {
        return ResponseEntity.created(URI.create("/api/v1/owner/" + owner.getId()))
                .body(ownerService.save(owner));
    }
    @GetMapping("/by-resource/{resourceId}/paginated")
    public ResponseEntity<Page<Owner>> getByResourceIdPaginated(
            @PathVariable String resourceId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(ownerService.getByResourceIdPaginated(resourceId, PageRequest.of(page, size)));
    }


    @GetMapping("/by-resource/{id}")
    public ResponseEntity<List<Owner>> getOwnersByResourceId(@PathVariable String id) {
        return ResponseEntity.ok(ownerService.getByResourceId(id));
    }
    @GetMapping("/{id}")
    public ResponseEntity<Owner> getOwnerById(@PathVariable String id) {
        return ownerService.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    @PutMapping
    public ResponseEntity<Owner> updateOwner(@RequestBody Owner owner) {
        return ResponseEntity.ok(ownerService.save(owner));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOwner(@PathVariable String id) {
        return ownerService.delete(id) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
