package com.zenith.blog.request;

import jakarta.validation.constraints.NotEmpty;

public class CommentRequest {
    @NotEmpty(message = "Comment is empty")
    private String comment;

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
