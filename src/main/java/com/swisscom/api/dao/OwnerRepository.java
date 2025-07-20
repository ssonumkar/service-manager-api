package com.swisscom.api.dao;

import com.swisscom.api.model.Owner;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface OwnerRepository extends MongoRepository<Owner, String> {

    // Custom query methods can be defined here if needed
    // For example, to find owners by a specific field:
    // List<Owner> findByServiceId(String serviceId);

    // Additional methods can be added as required for the application logic
    List<Owner> findByServiceId(String serviceId);
    List<Owner> findByResourceId(String resourceId);
}
