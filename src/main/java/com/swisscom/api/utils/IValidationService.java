package com.swisscom.api.utils;

public interface IValidationService {
    boolean isServiceValid(String serviceId);
    boolean isResourceValid(String resourceId);
}