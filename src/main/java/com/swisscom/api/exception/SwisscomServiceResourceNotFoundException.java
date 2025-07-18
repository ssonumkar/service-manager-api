package com.swisscom.api.exception;

import lombok.Getter;

@Getter
public class SwisscomServiceResourceNotFoundException extends RuntimeException{
    private final ErrorCode errorCode;

    public SwisscomServiceResourceNotFoundException(String message) {
        super(message);
        this.errorCode = ErrorCode.RESOURCE_NOT_FOUND;
    }
    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
