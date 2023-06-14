package com.zenith.blog.controller;

import com.zenith.blog.exception.ResourceNotFoundException;
import com.zenith.blog.model.Comment;
import com.zenith.blog.model.User;
import com.zenith.blog.repository.PostRepository;
import com.zenith.blog.request.CommentRequest;
import com.zenith.blog.response.CommentResponse;
import com.zenith.blog.response.UserResponse;
import com.zenith.blog.service.impl.CommentServiceImpl;
import com.zenith.blog.util.APIResponse;
import com.zenith.blog.util.GetAuthenticatedUserDetails;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/comments")
public class CommentController {
    private final CommentServiceImpl commentService;

    public CommentController(CommentServiceImpl commentService) {
        this.commentService = commentService;
    }

    @PostMapping
    public ResponseEntity<CommentResponse> addComment(@RequestParam("post_id") Long id, @Valid @RequestBody CommentRequest comment){
        return new ResponseEntity<>(commentService.addComment(id, comment), HttpStatus.CREATED);
    }
    @GetMapping("/{post_id}")
    public ResponseEntity<List<CommentResponse>> getAllCommentsByPostId(@PathVariable("post_id") Long postId, @RequestParam(value = "page", defaultValue = "1", required = false) Integer page, @RequestParam(value = "size", defaultValue = "10", required = false) Integer size){
        return ResponseEntity.ok(commentService.getAllCommentsByPostId(postId, page < 1 ? 0: page - 1, size < 1 ? 1 : size));
    }
    @PutMapping("/{comment_id}")
    public ResponseEntity<CommentResponse> updateById(@PathVariable("comment_id") Long id, @Valid @RequestBody CommentRequest commentRequest){
        return new ResponseEntity<>(commentService.updateCommentById(id, commentRequest), HttpStatus.OK);
    }
    @DeleteMapping("/{comment_id}")
    public APIResponse deleteById(@PathVariable("comment_id") Long id){
        return commentService.deleteCommentById(id);
    }
}
