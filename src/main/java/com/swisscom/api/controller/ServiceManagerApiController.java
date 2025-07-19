package com.swisscom.api.controller;

import com.swisscom.api.model.ServiceSummaryDTO;
import com.swisscom.api.model.SwisscomService;
import com.swisscom.api.service.SwisscomApiServiceManager;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/v1/service")
@AllArgsConstructor
public class ServiceManagerApiController {

    SwisscomApiServiceManager swisscomApiServiceManager;

    @PostMapping
    public ResponseEntity save(@Valid @RequestBody SwisscomService swisscomService){
        SwisscomService createdService = swisscomApiServiceManager.save(swisscomService);
        URI location = URI.create("/api/service/" + createdService.getId());
        return ResponseEntity.created(location).body(createdService);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SwisscomService> getService(@PathVariable String id){
        return swisscomApiServiceManager.get(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    @GetMapping
    public ResponseEntity<List<SwisscomService>> getAllServices(){
        List<SwisscomService> services = swisscomApiServiceManager.getAllServices();
        return ResponseEntity.ok(services);
    }

    @GetMapping("/summary")
    public ResponseEntity<List<ServiceSummaryDTO>> getAllServicesSummary(){
        List<ServiceSummaryDTO> services = swisscomApiServiceManager.getAllServicesSummaryFromCache();
        return ResponseEntity.ok(services);
    }
    @PutMapping
    public ResponseEntity<SwisscomService> updateService(@Valid @RequestBody SwisscomService swisscomService){
        if (this.swisscomApiServiceManager.get(swisscomService.getId()).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        SwisscomService updated = swisscomApiServiceManager.update(swisscomService);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        boolean deleted = swisscomApiServiceManager.delete(id);
        return deleted ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }

}
