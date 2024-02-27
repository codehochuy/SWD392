package com.example.swd392.serviceimplement;

import com.example.swd392.Request.CommentRequest.CreateCommentRequest;
import com.example.swd392.model.Comment;
import com.example.swd392.repository.ArtworkRepo;
import com.example.swd392.repository.CommentRepository;
import com.example.swd392.repository.UserRepo;
import com.example.swd392.service.ArtworkService;
import com.example.swd392.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class CommentServiceImplement implements CommentService {

    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private UserRepo userRepo;

    @Autowired
    private ArtworkRepo artworkRepo;

    @Override
    public Comment createComment(CreateCommentRequest request) {
        int userId = request.getUserId();
        int artworkId = request.getArtworkId();
        String commentText = request.getCommentText();

        Comment comment = Comment.builder()
                .commentText(commentText)
                .commentedAt(LocalDateTime.now())
                .user(userRepo.findUserByUsersID(userId).orElse(null))
                .artwork(artworkRepo.findByArtworkId(artworkId).orElse(null))
                .build();

        return commentRepository.save(comment);
    }
    }

