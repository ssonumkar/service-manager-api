package com.swisscom.api.service;

import com.swisscom.api.model.SwisscomService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ISwisscomServiceService {
    /**
     * Retrieves all services
     */
    List<SwisscomService> getAll();

    /**
     * Retrieves a service by ID
     */
    Optional<SwisscomService> getById(String id);

    /**
     * Saves a service
     */
    SwisscomService save(SwisscomService service);

    /**
     * Deletes a service by ID
     */
    boolean delete(String id);


    Page<SwisscomService> getAllPaginated(Pageable pageable);
}