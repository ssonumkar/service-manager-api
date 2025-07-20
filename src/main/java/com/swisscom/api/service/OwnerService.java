package com.swisscom.api.service;

import com.swisscom.api.dao.OwnerRepository;
import com.swisscom.api.model.Owner;
import com.swisscom.api.utils.ValidationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class OwnerService implements IOwnerService{
    private final OwnerRepository repository;
    private final ResourceService resourceService;
    private final SwisscomServiceService swisscomService;
    private final ValidationService validationService;

    @Cacheable(value = "owners", key = "#resourceId", sync = true)
    public List<Owner> getByResourceId(String resourceId) {
        log.info("Fetching owners by resourceId: {}", resourceId);
        return repository.findByResourceId(resourceId);
    }

    @Override
    @Cacheable(value = "ownersByResource", key = "#resourceId", sync = true)
    public Page<Owner> getByResourceIdPaginated(String resourceId, Pageable pageable) {
        log.info("Fetching paginated owners by resourceId: {}", resourceId);
        return repository.findByResourceId(resourceId, pageable);
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

    @CacheEvict(value = {"owners", "ownersByService", "ownersByResource"}, allEntries = true)
    public void saveAll(List<Owner> ownersToSave, String serviceId) {
        log.info("Saving multiple owners");
        repository.saveAll(ownersToSave);
    }
    private void validateServiceAndResource(Owner owner) {
        validationService.validateServiceAndResource(owner.getServiceId(), owner.getResourceId());
    }
    @Override
    @Cacheable(value = "owners", key = "#id", sync = true)
    public Optional<Owner> getById(String id) {
        log.info("Fetching owner by id: {}", id);
        return repository.findById(id);
    }
}