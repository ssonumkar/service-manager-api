package com.swisscom.api.service;

import com.swisscom.api.dao.ResourceRepository;
import com.swisscom.api.model.Resource;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ResourceService {
    private final ResourceRepository repository;
    private final SwisscomServiceService swisscomService;
    @Cacheable(value = "resources")
    public List<Resource> getAll() {
        log.info("Fetching all resources");
        return repository.findAll();
    }

    @Cacheable(value = "resourcesByService", key = "#serviceId")
    public List<Resource> getByServiceId(String serviceId) {
        log.info("Fetching resources by serviceId: {}", serviceId);
        return repository.findByServiceId(serviceId);
    }

    @CacheEvict(value = {"resources", "resourcesByService"}, allEntries = true)
    public Resource save(Resource resource) {
        log.info("Saving resource: {}", resource);
        validateResourceId(resource.getServiceId());
        return repository.save(resource);
    }

    @CacheEvict(value = {"resources", "resourcesByService"}, allEntries = true)
    public boolean delete(String id) {
        log.info("Deleting resource with id: {}", id);
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }

    public void saveAll(List<Resource> resourcesToSave) {
        log.info("Saving multiple resources");
        repository.saveAll(resourcesToSave);
    }
    private void validateResourceId(String serviceId) {
        boolean exists = swisscomService.getById(serviceId).isPresent();
        if (!exists) {
            log.warn("Invalid service ID referenced in resource: {}", serviceId);
            throw new IllegalArgumentException("Invalid service ID: " + serviceId);
        }
    }

    public Optional<Resource> getByResourceId(String resourceId) {
        return repository.findById(resourceId);
    }
}