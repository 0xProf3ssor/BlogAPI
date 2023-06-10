package com.zenith.blog.exception;

public class ExtensionNotAllowedException extends RuntimeException {
    public ExtensionNotAllowedException(String extension){
        super(String.format("%s extension is not allowed, only jpeg, jpg, png are allowed", extension));
    }
}
