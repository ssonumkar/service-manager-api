package com.swisscom.api.service;

import com.swisscom.api.dao.OwnerRepository;
import com.swisscom.api.model.Owner;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class OwnerService {
    private final OwnerRepository repository;
    private final ResourceService resourceService;
    private final SwisscomServiceService swisscomService;

    @Cacheable(value = "owners")
    public List<Owner> getAll() {
        log.info("Fetching all owners");
        return repository.findAll();
    }

    @Cacheable(value = "ownersByService", key = "#serviceId")
    public List<Owner> getByServiceId(String serviceId) {
        log.info("Fetching owners by serviceId: {}", serviceId);
        return repository.findByServiceId(serviceId);
    }

    @Cacheable(value = "ownersByResource", key = "#resourceId")
    public List<Owner> getByResourceId(String resourceId) {
        log.info("Fetching owners by resourceId: {}", resourceId);
        return repository.findByResourceId(resourceId);
    }

    @CacheEvict(value = {"owners", "ownersByService", "ownersByResource"}, allEntries = true)
    public Owner save(Owner owner) {
        log.info("Saving owner: {}", owner);
        validateServiceAndResource(owner);
        return repository.save(owner);
    }

    @CacheEvict(value = {"owners", "ownersByService", "ownersByResource"}, allEntries = true)
    public boolean delete(String id) {
        log.info("Deleting owner with id: {}", id);
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }

    public void saveAll(List<Owner> ownersToSave, String serviceId) {
        log.info("Saving multiple owners");
        repository.saveAll(ownersToSave);
    }
    private void validateServiceAndResource(Owner owner) {
        String resourceId = owner.getResourceId();
        String serviceId = owner.getServiceId();
        boolean serviceExists = swisscomService.getById(serviceId).isPresent();

        if (!serviceExists) {
            log.warn("Invalid service ID referenced in Owner: {}", serviceId);
            throw new IllegalArgumentException("Invalid service ID: " + serviceId);
        }
        Boolean resourceExists = resourceService.getByResourceId(resourceId).isPresent();

        if (!resourceExists) {
            log.warn("Invalid resource ID referenced in Owner: {}", resourceId);
            throw new IllegalArgumentException("Invalid resource ID: " + resourceId);
        }
    }
}