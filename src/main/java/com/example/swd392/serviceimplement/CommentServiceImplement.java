package com.example.swd392.serviceimplement;

import com.example.swd392.Request.CommentRequest.CreateCommentRequest;
import com.example.swd392.Response.ObjectResponse.ResponseObject;
import com.example.swd392.enums.Role;
import com.example.swd392.model.Artwork;
import com.example.swd392.model.Comment;
import com.example.swd392.model.User;
import com.example.swd392.repository.ArtworkRepo;
import com.example.swd392.repository.CommentRepository;
import com.example.swd392.repository.UserRepo;
import com.example.swd392.service.ArtworkService;
import com.example.swd392.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class CommentServiceImplement implements CommentService {

    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private UserRepo userRepo;

    @Autowired
    private ArtworkRepo artworkRepo;


    @Override
    public ResponseEntity<ResponseObject> createComment(CreateCommentRequest createCommentRequest) {
        try {
            int userId = createCommentRequest.getUserId();
            int artworkId = createCommentRequest.getArtworkId();
            User user = userRepo.findUserByUsersID(userId).orElse(null);
            Artwork artwork = artworkRepo.findByArtworkId(artworkId).orElse(null);
            if (user == null || artwork == null) {
                return ResponseEntity.ok(new ResponseObject(
                        "Fail",
                        "User or artwork not found",
                        null));
            }


            if (user.getRole() != Role.AUDIENCE && user.getRole() != Role.CREATOR) {
                return ResponseEntity.ok(new ResponseObject(
                        "Fail",
                        "User does not have permission to create comment",
                        null));
            }


            Comment comment = new Comment();
            comment.setCommentText(createCommentRequest.getCommentText());
            comment.setCommentedAt(new Date());
            comment.setUser(user);
            comment.setArtwork(artwork);


            Comment savedComment = commentRepository.save(comment);
            artwork.setCommentCount(artwork.getCommentCount()+1);

            return ResponseEntity.ok(new ResponseObject(
                    "Success",
                    "Create comment success",
                    savedComment));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new ResponseObject(
                    "Fail",
                    "Internal Server Error",
                    null));
        }
    }


    @Override
    public ResponseEntity<ResponseObject> updateComment(Integer commentId, CreateCommentRequest createCommentRequest) {
        try {
            Comment existingComment = commentRepository.findById(commentId).orElse(null);
            if (existingComment == null) {

            }
            existingComment.setCommentText(createCommentRequest.getCommentText());
            existingComment.setCommentedAt(new Date());

            Comment updatedComment = commentRepository.save(existingComment);
            return ResponseEntity.ok(new ResponseObject("Success", "Update Comment success", updatedComment));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new ResponseObject("Fail", "Internal Server Error", null));
        }
    }

    @Override
    public ResponseEntity<ResponseObject> deleteComment(Integer commentId) {
        try {
            Comment comment = commentRepository.findById(commentId).orElse(null);
            if (comment == null) {

            }
            commentRepository.delete(comment);
            return ResponseEntity.ok(new ResponseObject("Success", "Delete Comment success", null));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new ResponseObject("Fail", "Internal Server Error", null));
        }
    }

    @Override
    public ResponseEntity<ResponseObject> findCommentById(Integer commentId) {
        try {
            Comment comment = commentRepository.findById(commentId).orElse(null);
            if (comment == null) {

            }
            return ResponseEntity.ok(new ResponseObject("Success", "Find Comment success", comment));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new ResponseObject("Fail", "Internal Server Error", null));
        }
    }

    @Override
    public List<Comment> getAllComments() {
        return commentRepository.findAll();
    }


    @Override
    public List<Comment> searchCommentsFilter(String commentText, Date commentedAt) {
        if (commentText != null && commentedAt != null) {
            return commentRepository.findByCommentTextContainingAndCommentedAt(commentText, commentedAt);
        } else if (commentText != null) {
            return commentRepository.findByCommentTextContaining(commentText);
        } else if (commentedAt != null) {
            return commentRepository.findByCommentedAt(commentedAt);
        } else {
            return commentRepository.findAll();
        }
    }

    @Override
    public List<Comment> getAllCommentsByArtworkId(int artworkId) {
        List<Comment> comments = commentRepository.findByArtwork_ArtworkId(artworkId);
        if (comments == null || comments.isEmpty()) {
            return Collections.emptyList();
        }
        return comments;
    }


}


