package com.zenith.blog.request;


import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.web.multipart.MultipartFile;

public class PostRequest {
    @NotEmpty(message = "Post text is required")
    @Max(value = 500, message = "Max 500 characters allowed")
    private String text;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

}
