package com.swisscom.api.service;

import com.swisscom.api.model.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface IResourceService {
    /**
     * Saves a resource
     */
    Resource save(Resource resource);

    /**
     * Retrieves resources by service ID
     */
    List<Resource> getByServiceId(String serviceId);


    /**
     * Deletes a resource by ID
     */
    boolean delete(String id);

    void saveAll(List<Resource> resourcesToSave);
    /**
     * Get paginated list of all resources
     */

    /**
     * Get paginated list of resources by service ID
     */
    Page<Resource> getByServiceIdPaginated(String serviceId, Pageable pageable);

    Optional<Resource> getByResourceId(String resourceId);

    void deleteByServiceId(String id);
}