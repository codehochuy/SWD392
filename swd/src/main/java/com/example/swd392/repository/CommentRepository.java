package com.example.swd392.repository;

import com.example.swd392.model.Artwork;
import com.example.swd392.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface CommentRepository extends JpaRepository<Comment,Integer> {

    List<Comment> findByCommentTextContainingAndCommentedAt(String commentText, Date commentedAt);

    List<Comment> findByCommentTextContaining(String commentText);

    List<Comment> findByCommentedAt(Date commentedAt);
}
