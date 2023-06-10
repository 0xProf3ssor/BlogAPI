package com.zenith.blog.service.impl;

import com.zenith.blog.exception.ResourceNotFoundException;
import com.zenith.blog.model.Post;
import com.zenith.blog.model.PostImage;
import com.zenith.blog.model.User;
import com.zenith.blog.repository.PostRepository;
import com.zenith.blog.repository.UserRepository;
import com.zenith.blog.request.PostRequest;
import com.zenith.blog.response.PostResponse;
import com.zenith.blog.service.PostService;
import com.zenith.blog.util.FileUploader;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final FileUploader fileUploader;
    @Value("${post.image.path}")
    private String POST_IMAGE_UPLOAD_DIR;

    public PostServiceImpl(PostRepository postRepository, UserRepository userRepository, ModelMapper modelMapper, FileUploader fileUploader) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.fileUploader = fileUploader;
    }

    @Override
    public List<PostResponse> getAll(Integer pageNumber, Integer pageSize) {
        Page<Post> pagePost = postRepository.findAll(PageRequest.of(pageNumber, pageSize));
        List<PostResponse> posts = pagePost
                .getContent()
                .stream()
                .map(post -> modelMapper.map(post, PostResponse.class))
                .collect(Collectors.toList());
        return posts;
    }

    @Override
    public PostResponse create(PostRequest postRequest, MultipartFile[] images, Long userId) {
        //Check user valid or not
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "id", userId.toString()));


        //Map to Post for saving into database
        Post post = modelMapper.map(postRequest, Post.class);

        //Add user to Post
        post.setUser(user);

        //Upload images if exists
        if(images != null && images.length > 0){
            List<PostImage> postImages = fileUploader.uploadPostImages(images, POST_IMAGE_UPLOAD_DIR);
            //Add images to Post
            post.setImages(postImages);
        }



        return modelMapper.map(postRepository.save(post), PostResponse.class);
    }

    @Override
    public PostResponse getById(Long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id.toString()));
        return modelMapper.map(post, PostResponse.class);
    }

    @Override
    public PostResponse updateById(Long id, PostRequest postRequest) {
        return null;
    }

    @Override
    public List<PostResponse> getByUserId(Long id) {
        List<Post> posts = postRepository.findByUserId(id);
        return posts
                .stream()
                .map(post -> modelMapper.map(post, PostResponse.class))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(Long id) {
        postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id.toString()));
        postRepository.deleteById(id);
    }
}
