package com.zenith.blog.exception;

public class OperationNotAllowedException extends RuntimeException {
    public OperationNotAllowedException(){
        super("This operation is not allowed for logged in user");
    }
}
