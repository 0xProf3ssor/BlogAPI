package com.zenith.blog.util;

import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.function.Consumer;

@Component
public class FileDeleter implements Consumer<String> {
    @Override
    public void accept(String path) {
        try {
            Files.deleteIfExists(Path.of(path));
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
