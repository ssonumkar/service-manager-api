package com.swisscom.api.service;

import com.swisscom.api.model.Owner;
import com.swisscom.api.model.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Optional;

public interface IOwnerService {

    /**
     * Retrieves owners by resource ID
     */
    List<Owner> getByResourceId(String resourceId);

    /**
     * Retrieves paginated owners by service ID
     */

    /**
     * Retrieves paginated owners by resource ID
     */
    Page<Owner> getByResourceIdPaginated(String resourceId, Pageable pageable);


    /**
     * Saves an owner
     */
    Owner save(Owner owner);

    /**
     * Saves multiple owners for a service
     */
    void saveAll(List<Owner> owners, String serviceId);

    /**
     * Deletes an owner by ID
     */
    boolean delete(String id);

    Optional<Owner> getById(String id);
}