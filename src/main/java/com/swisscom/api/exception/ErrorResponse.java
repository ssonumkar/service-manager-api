package com.swisscom.api.exception;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
@Data
@Builder
public class ErrorResponse {
    private ErrorCode errorCode;
    private String errMessage;
    private LocalDateTime timestamp;
    private String path;
}
