package com.swisscom.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@ControllerAdvice
public class SwisscomApiExceptionHandler{
    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleResourceNotFound(SwisscomServiceResourceNotFoundException ex, WebRequest request){
        ErrorResponse errorResponse = ErrorResponse.builder()
                .errorCode(ex.getErrorCode())
                .errMessage("Invalid Input swisscom service resource.")
                .timestamp(LocalDateTime.now())
                .path(request.getContextPath())
                .build();
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleInputValidationException(MethodArgumentNotValidException ex, WebRequest req){
        ErrorResponse errorResponse = ErrorResponse.builder()
                .errorCode(ErrorCode.INVALID_INPUT)
                .errMessage("Invalid input")
                .timestamp(LocalDateTime.now())
                .path(req.getDescription(false).replace("URI", ""))
                .build();
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleGenericException(Exception ex, WebRequest request){
        ErrorResponse errorResponse = ErrorResponse.builder()
                .errorCode(ErrorCode.INTERNAL_ERROR)
                .errMessage("unexpected error happened")
                .timestamp(LocalDateTime.now())
                .path(request.getDescription(false).replace("uri=", ""))
                .build();

        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleSerializationExceptions(HttpMessageConversionException ex, WebRequest req){
        ErrorResponse errorResponse = ErrorResponse.builder()
                .errorCode(ErrorCode.INVALID_INPUT)
                .errMessage("Invalid JSON input: "+ ex.getMostSpecificCause().getMessage())
                .timestamp(LocalDateTime.now())
                .path(req.getDescription(false).replace("URI", ""))
                .build();
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}
