package com.zenith.blog.response;

import java.util.List;

public class PostResponse {
    private Long id;
    private String text;
    private UserResponse user;
    private List<PostImageResponse> images;

    public List<PostImageResponse> getImages() {
        return images;
    }

    public void setImages(List<PostImageResponse> images) {
        this.images = images;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public UserResponse getUser() {
        return user;
    }

    public void setUser(UserResponse user) {
        this.user = user;
    }
}
