package com.swisscom.api.service;

import com.swisscom.api.dao.SwisscomServiceRepository;
import com.swisscom.api.model.SwisscomService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Profile({"dev", "prod"})
@AllArgsConstructor
public class SwisscomApiServiceManager {
    private final SwisscomServiceRepository repository;
    private final SwisscomServiceCache cache;

    public SwisscomService save(SwisscomService swisscomService) {
        if (!cache.contains(swisscomService.getId())) {
            cache.putIfAbsent(swisscomService.getId(), swisscomService);
            return this.repository.save(swisscomService);
        }
        return cache.get(swisscomService.getId()).orElse(null);
    }

    public Optional<SwisscomService> get(String id) {
        return cache.get(id).or(() -> repository.findById(id));
    }

    public SwisscomService update(SwisscomService swisscomService) {
        this.cache.put(swisscomService.getId(), swisscomService);
        return this.repository.save(swisscomService);
    }

    public boolean delete(String id) {
        this.cache.remove(id);
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }

    public void clearCache(){
        this.cache.clear();
    }

    public List<SwisscomService> getAllServices() {
        if (cache.isEmpty()) {
            return repository.findAll();
        } else {
            return cache.getAll();
        }
    }
}
