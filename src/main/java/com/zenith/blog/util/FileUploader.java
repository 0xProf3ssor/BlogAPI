package com.zenith.blog.util;

import com.zenith.blog.exception.ExtensionNotAllowedException;
import com.zenith.blog.model.PostImage;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class FileUploader {
    public List<PostImage> uploadPostImages(MultipartFile[] images, String uploadPath){

        return Arrays
                .stream(images)
                .map(image -> {

                    String imageName = UUID.randomUUID().toString();
                    String originalFilename = image.getOriginalFilename();
                    String extension = getFileExtension(originalFilename);
                    boolean isValidImage = image.getContentType().equals("image/jpeg") || image.getContentType().equals("image/png");
                    if(!isValidImage) throw new ExtensionNotAllowedException(extension);

                    //Upload path
                    Path uploadDirectory = Path.of(uploadPath);
                    Path filePath = Path.of(uploadPath, imageName + "." + extension);
                    if(!Files.exists(uploadDirectory)) {
                        try {
                            Files.createDirectory(uploadDirectory);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    try {

                        Files.copy(image.getInputStream(), filePath);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    PostImage postImage = new PostImage();
                    postImage.setPath(filePath.toString());
                    return postImage;
                })
                .collect(Collectors.toList());
    }
    public String getFileExtension(String filename){
        int dotIndex = filename.lastIndexOf(".");
        if(dotIndex == -1 || dotIndex == filename.length() - 1) return "";
        return filename.substring(dotIndex + 1);
    }
}
