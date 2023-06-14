package com.zenith.blog.service;

import com.zenith.blog.model.Comment;
import com.zenith.blog.request.CommentRequest;
import com.zenith.blog.response.CommentResponse;
import com.zenith.blog.util.APIResponse;

import java.util.List;

public interface CommentService {
    //Add comment
    CommentResponse addComment(Long postId, CommentRequest comment);

    //Get All Comments By Post ID
    List<CommentResponse> getAllCommentsByPostId(Long postId, Integer pageNumber, Integer pageSize);

    //Delete Comment By ID
    APIResponse deleteCommentById(Long id);

    //Update Comment By ID
    CommentResponse updateCommentById(Long id, CommentRequest comment);
}
