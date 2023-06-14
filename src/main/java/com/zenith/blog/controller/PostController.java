package com.zenith.blog.controller;

import com.zenith.blog.request.PostRequest;
import com.zenith.blog.response.PostResponse;
import com.zenith.blog.service.PostService;
import com.zenith.blog.util.APIResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class PostController {
    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    //Get all posts
    @GetMapping("/posts")
    public ResponseEntity<List<PostResponse>> getAllPosts(@RequestParam(value = "page", defaultValue = "1", required = false) Integer page, @RequestParam(value = "size", defaultValue = "10", required = false) Integer size){
        return  ResponseEntity.ok(postService.getAll(page < 1 ? 0: page - 1, size < 1 ? 1 : size));
    }

    //Create post
    @PostMapping(value = "/posts")
    public ResponseEntity<PostResponse> createPost(@RequestPart(value = "images", required = false) MultipartFile[] images, @Valid @RequestPart("post") PostRequest postRequest){

        return new ResponseEntity<>(postService.create(postRequest, images), HttpStatus.CREATED);
    }

    //Update post by id
    @PutMapping("/posts/{id}")
    public ResponseEntity<PostResponse> updateById(@PathVariable Long id, @Valid @RequestBody PostRequest postRequest){
        return new ResponseEntity<>(postService.updateById(id, postRequest), HttpStatus.OK);
    }

    //Add image to post
    @PutMapping("/posts/{id}/add-image")
    public ResponseEntity<PostResponse> addImage(@PathVariable("id") Long id, @RequestPart("images") MultipartFile[] images){
        return ResponseEntity.ok(postService.addPostImages(id, images));
    }

    //Get by id
    @GetMapping(value = "/posts/{id}")
    public ResponseEntity<PostResponse> getById(@PathVariable("id") Long id){
        return ResponseEntity.ok(postService.getById(id));
    }

    //Get by User ID
    @GetMapping("users/{user_id}/posts")
    public ResponseEntity<List<PostResponse>> getByUserId(@PathVariable("user_id") Long id){
        return ResponseEntity.ok(postService.getByUserId(id));
    }
    //Delete Post Image
    @PutMapping("/posts/{post_id}/images/{image_id}")
    public ResponseEntity<PostResponse> deletePostImage(@PathVariable("post_id") Long postId, @PathVariable("image_id") Long imgId){
        return ResponseEntity.ok(postService.deletePostImage(postId, imgId));
    }

    //Delete by ID
    @DeleteMapping("/posts/{id}")
    public ResponseEntity<APIResponse> deletePostById(@PathVariable("id") Long id){

        return ResponseEntity.ok(postService.deleteById(id));
    }
}