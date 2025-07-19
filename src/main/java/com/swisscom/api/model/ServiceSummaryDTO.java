package com.swisscom.api.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class ServiceSummaryDTO {
    String id;
    Integer resourceCount;
}
