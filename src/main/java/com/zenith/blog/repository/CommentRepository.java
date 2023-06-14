package com.zenith.blog.repository;

import com.zenith.blog.model.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CommentRepository  extends JpaRepository<Comment, Long> {
    //Get Comment By Post ID
    Page<Comment> findByPostId(Long postId, Pageable pageable);
}
