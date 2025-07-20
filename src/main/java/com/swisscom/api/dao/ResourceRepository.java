package com.swisscom.api.dao;

import com.swisscom.api.model.Resource;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ResourceRepository extends MongoRepository<Resource, String> {

    // Custom query methods can be defined here if needed
    // For example, to find resources by a specific field:
    // List<Resource> findByFieldName(String fieldName);

    // Additional methods can be added as required for the application logic
    List<Resource> findByServiceId(String serviceId);

}
