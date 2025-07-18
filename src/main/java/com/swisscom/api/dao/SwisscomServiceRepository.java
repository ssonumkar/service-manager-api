package com.swisscom.api.dao;

import com.swisscom.api.model.SwisscomService;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SwisscomServiceRepository extends MongoRepository<SwisscomService, String> {
}
