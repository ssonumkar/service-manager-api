package com.swisscom.api.service;

import com.swisscom.api.dao.SwisscomServiceRepository;
import com.swisscom.api.model.SwisscomService;
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
public class SwisscomServiceService implements ISwisscomServiceService {
    private final SwisscomServiceRepository repository;

    @Cacheable(value = "services")
    public List<SwisscomService> getAll() {
        log.info("Fetching all services");
        return repository.findAll();
    }

    @Cacheable(value = "service", key = "#id")
    public Optional<SwisscomService> getById(String id) {
        log.info("Fetching service by id: {}", id);
        return repository.findById(id);
    }

    @CacheEvict(value = {"services", "service"}, allEntries = true)
    public SwisscomService save(SwisscomService service) {
        log.info("Saving service: {}", service);
        return repository.save(service);
    }

    @CacheEvict(value = {"services", "service"}, allEntries = true)
    public boolean delete(String id) {
        log.info("Deleting service with id: {}", id);
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public Page<SwisscomService> getAllPaginated(Pageable pageable) {
        log.info("Fetching paginated services");
        return repository.findAll(pageable);
    }

}