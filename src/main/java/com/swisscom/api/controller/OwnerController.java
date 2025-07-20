package com.swisscom.api.controller;

import com.swisscom.api.model.Owner;
import com.swisscom.api.service.OwnerService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/v1/owner")
@AllArgsConstructor
public class OwnerController {
    private final OwnerService ownerService;
    @PostMapping
    public ResponseEntity<Owner> createOwner(@RequestBody Owner owner) {
        return ResponseEntity.created(URI.create("/api/v1/owner/" + owner.getId()))
                .body(ownerService.save(owner));
    }
    @GetMapping("/by-service/{id}")
    public ResponseEntity<List<Owner>> getOwnersByServiceId(@PathVariable String id) {
        return ResponseEntity.ok(ownerService.getByServiceId(id));
    }

    @GetMapping("/by-resource/{id}")
    public ResponseEntity<List<Owner>> getOwnersByResourceId(@PathVariable String id) {
        return ResponseEntity.ok(ownerService.getByResourceId(id));
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
