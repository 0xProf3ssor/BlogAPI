package com.zenith.blog.model;

import jakarta.persistence.*;

@Entity
@Table(name = "reacts")
public class React {
    @Id
    @GeneratedValue
    private Long id;
    @Enumerated(EnumType.STRING)
    private ReactType react;
    @ManyToOne
    private Post post;
    @ManyToOne
    private User user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ReactType getReact() {
        return react;
    }

    public void setReact(ReactType react) {
        this.react = react;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
