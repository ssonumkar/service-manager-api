package com.swisscom.api.utils;

import com.swisscom.api.dao.ResourceRepository;
import com.swisscom.api.dao.SwisscomServiceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ValidationService implements IValidationService {
    private final SwisscomServiceRepository swisscomServiceRepository;
    private final ResourceRepository resourceRepository;

    @Override
    public boolean isServiceValid(String serviceId) {
        return swisscomServiceRepository.findById(serviceId).isPresent();
    }

    @Override
    public boolean isResourceValid(String resourceId) {
        return resourceRepository.findById(resourceId).isPresent();
    }

    public void validateServiceAndResource(String serviceId, String resourceId) {
        if (!isServiceValid(serviceId)) {
            throw new IllegalArgumentException("Invalid service ID: " + serviceId);
        }
        if (!isResourceValid(resourceId)) {
            throw new IllegalArgumentException("Invalid resource ID: " + resourceId);
        }
    }
}
