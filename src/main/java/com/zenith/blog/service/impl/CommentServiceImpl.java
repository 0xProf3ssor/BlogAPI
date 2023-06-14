package com.zenith.blog.service.impl;

import com.zenith.blog.exception.OperationNotAllowedException;
import com.zenith.blog.exception.ResourceNotFoundException;
import com.zenith.blog.model.Comment;
import com.zenith.blog.model.Post;
import com.zenith.blog.model.User;
import com.zenith.blog.repository.CommentRepository;
import com.zenith.blog.repository.PostRepository;
import com.zenith.blog.request.CommentRequest;
import com.zenith.blog.response.CommentResponse;
import com.zenith.blog.service.CommentService;
import com.zenith.blog.util.APIResponse;
import com.zenith.blog.util.GetAuthenticatedUserDetails;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final ModelMapper modelMapper;
    private final GetAuthenticatedUserDetails authenticatedUserDetails;

    public CommentServiceImpl(CommentRepository commentRepository, PostRepository postRepository, ModelMapper modelMapper, GetAuthenticatedUserDetails authenticatedUserDetails) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.modelMapper = modelMapper;
        this.authenticatedUserDetails = authenticatedUserDetails;
    }

    @Override
    public CommentResponse addComment(Long postId, CommentRequest commentRequest) {
        Comment comment = modelMapper.map(commentRequest, Comment.class);
        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId.toString()));

        comment.setPost(post);
        comment.setUser(authenticatedUserDetails.getDetails());

        Comment savedComment = commentRepository.save(comment);
        return modelMapper.map(savedComment, CommentResponse.class);
    }

    @Override
    public List<CommentResponse> getAllCommentsByPostId(Long postId, Integer pageNumber, Integer pageSize) {
        Page<Comment> byPostId = commentRepository.findByPostId(postId, PageRequest.of(pageNumber, pageSize));
        return byPostId
                .getContent()
                .stream()
                .map(comment -> modelMapper.map(comment, CommentResponse.class))
                .collect(Collectors.toList());
    }

    @Override
    public APIResponse deleteCommentById(Long id) {
        Comment commentToDelete = commentRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Comment", "id", id.toString()));
        User postOwner = commentToDelete.getPost().getUser();
        User commentOwner = commentToDelete.getUser();
        User currentUser = authenticatedUserDetails.getDetails();
        if(!currentUser.getId().equals(postOwner.getId()) || !currentUser.getId().equals(commentOwner.getId())) throw new OperationNotAllowedException();
        commentRepository.deleteById(id);
        return new APIResponse(false, "Deleted", "Comment deleted successfully");
    }

    @Override
    public CommentResponse updateCommentById(Long id, CommentRequest comment) {
        Comment commentToUpdate = commentRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Comment", "id", id.toString()));
        Long commentOwnerId = commentToUpdate.getUser().getId();
        Long postOwnerId = commentToUpdate.getPost().getUser().getId();
        Long currentUserId = authenticatedUserDetails.getDetails().getId();
        if(!currentUserId.equals(postOwnerId) || !currentUserId.equals(commentOwnerId)) throw new OperationNotAllowedException();
        commentToUpdate.setComment(comment.getComment());
        commentRepository.save(commentToUpdate);
        return modelMapper.map(commentToUpdate, CommentResponse.class);
    }

}
