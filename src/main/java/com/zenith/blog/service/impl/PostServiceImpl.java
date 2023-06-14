package com.zenith.blog.service.impl;

import com.zenith.blog.exception.ResourceNotFoundException;
import com.zenith.blog.model.Post;
import com.zenith.blog.model.PostImage;
import com.zenith.blog.repository.PostRepository;
import com.zenith.blog.repository.UserRepository;
import com.zenith.blog.request.PostRequest;
import com.zenith.blog.response.PostResponse;
import com.zenith.blog.service.PostService;
import com.zenith.blog.util.APIResponse;
import com.zenith.blog.util.FileDeleter;
import com.zenith.blog.util.FileUploader;
import com.zenith.blog.util.GetAuthenticatedUserDetails;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    private final GetAuthenticatedUserDetails authenticatedUserDetails;
    private final ModelMapper modelMapper;
    private final FileUploader fileUploader;
    @Value("${post.image.path}")
    private String POST_IMAGE_UPLOAD_DIR;
    private final FileDeleter fileDeleter;

    public PostServiceImpl(PostRepository postRepository, GetAuthenticatedUserDetails authenticatedUserDetails, ModelMapper modelMapper, FileUploader fileUploader, FileDeleter fileDeleter) {
        this.postRepository = postRepository;
        this.authenticatedUserDetails = authenticatedUserDetails;
        this.modelMapper = modelMapper;
        this.fileUploader = fileUploader;
        this.fileDeleter = fileDeleter;
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
    public PostResponse create(PostRequest postRequest, MultipartFile[] images) {
        //Map to Post for saving into database
        Post post = modelMapper.map(postRequest, Post.class);

        //Add user to Post
        post.setUser(authenticatedUserDetails.getDetails());

        //Upload images if exists
        if(images != null && images.length > 0){
            List<PostImage> postImages = fileUploader.uploadPostImages(images, POST_IMAGE_UPLOAD_DIR);
            //Add images to Post
            post.setImages(postImages);
        }

        Post savedPost = postRepository.save(post);
        return modelMapper.map(savedPost, PostResponse.class);
    }

    @Override
    public PostResponse getById(Long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id.toString()));
        System.out.println(post.getImages());
        return modelMapper.map(post, PostResponse.class);
    }

    @Override
    public PostResponse updateById(Long id, PostRequest postRequest) {
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id.toString()));
        post.setText(postRequest.getText());
        return modelMapper.map(post, PostResponse.class);
    }

    @Override
    public PostResponse addPostImages(Long id, MultipartFile[] images) {
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id.toString()));
        if(images != null && images.length > 0){
            List<PostImage> postImages = fileUploader.uploadPostImages(images, POST_IMAGE_UPLOAD_DIR);
            //Add images to Post
            List<PostImage> oldImages = post.getImages();
            oldImages.addAll(postImages);
        }
        return modelMapper.map(postRepository.save(post), PostResponse.class);
    }

    @Override
    public PostResponse deletePostImage(Long postId, Long imgId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId.toString()));

        List<PostImage> imgList = post.getImages().stream().filter(img -> img.getId().equals(imgId)).toList();
        if(imgList.size() == 0) throw new ResourceNotFoundException("Image", "id", imgId.toString());
        String imgPath = imgList.get(0).getPath();

        //delete image from file system
        fileDeleter.accept(imgPath);

        List<PostImage> images = post
                .getImages()
                .stream()
                .filter(img -> !img.getId().equals(imgId))
                .toList();
        post.setImages(images);
        return modelMapper.map(post, PostResponse.class);
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
    public APIResponse deleteById(Long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id.toString()));
        post.getImages().forEach(img -> fileDeleter.accept(img.getPath()));
        postRepository.deleteById(id);
        return new APIResponse(true, "Deleted", "Post deleted successfully!");
    }
}
