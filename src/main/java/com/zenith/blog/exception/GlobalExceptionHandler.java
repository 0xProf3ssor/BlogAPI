package com.zenith.blog.exception;

import com.zenith.blog.util.APIResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationException(MethodArgumentNotValidException ex) {
        Map<String, String> errorRes = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(err -> errorRes.put(err.getField(), err.getDefaultMessage()));
        return ResponseEntity.badRequest().body(errorRes);
    }
    @ExceptionHandler(ResourceNotFoundException.class)
    public  ResponseEntity<APIResponse> resourceNotFoundExceptionHandler(ResourceNotFoundException ex){
        APIResponse errorResponse = new APIResponse();
        errorResponse.setSuccess(false);
        errorResponse.setStatus("Not Found");
        errorResponse.setMessage(ex.getMessage());
        return  new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(ExtensionNotAllowedException.class)
    public ResponseEntity<APIResponse> extensionNotAllowedExceptionHandler(ExtensionNotAllowedException ex){
        APIResponse errorResponse = new APIResponse();
        errorResponse.setSuccess(false);
        errorResponse.setStatus("Extension Not Allowed");
        errorResponse.setMessage(ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}
