package com.zenith.blog.request;


import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class PostRequest {
    @NotEmpty(message = "Post text is required")
    @Size(min = 10, max = 500, message = "Range 10-500")
    private String text;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

}
