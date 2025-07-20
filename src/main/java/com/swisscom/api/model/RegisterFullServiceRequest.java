package com.swisscom.api.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegisterFullServiceRequest {
    private String serviceId;
    private List<ResourceDTO> resources;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ResourceDTO {
        private String resourceId;
        private List<Owner> owners;
    }
}

