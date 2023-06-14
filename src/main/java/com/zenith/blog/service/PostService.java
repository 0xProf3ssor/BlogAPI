package com.zenith.blog.service;

import com.zenith.blog.request.PostRequest;
import com.zenith.blog.response.PostResponse;
import com.zenith.blog.util.APIResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface PostService {
    //Get all posts
    List<PostResponse> getAll(Integer pageNumber, Integer pageSize);

    //Create post
    PostResponse create(PostRequest postRequest, MultipartFile[] images);

    //Get single post by id
    PostResponse getById(Long id);

    //Update Post
    PostResponse updateById(Long id, PostRequest postRequest);

    //Update Post Images
    PostResponse addPostImages(Long id, MultipartFile[] images);

    //Delete Post Image
    PostResponse deletePostImage(Long postId, Long imgId);

    //Get post by User id
    List<PostResponse> getByUserId(Long id);

    //Delete by id
    APIResponse deleteById(Long id);
}
