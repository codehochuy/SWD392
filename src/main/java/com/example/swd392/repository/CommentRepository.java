package com.example.swd392.repository;

import com.example.swd392.model.Artwork;
import com.example.swd392.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment,Integer> {
}
