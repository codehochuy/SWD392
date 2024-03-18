package com.example.swd392.serviceimplement;

import com.example.swd392.Request.CommentRequest.CreateCommentRequest;
import com.example.swd392.Response.ObjectResponse.ResponseObject;
import com.example.swd392.enums.Role;
import com.example.swd392.model.Comment;
import com.example.swd392.repository.ArtworkRepo;
import com.example.swd392.repository.CommentRepository;
import com.example.swd392.repository.UserRepo;
import com.example.swd392.service.ArtworkService;
import com.example.swd392.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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
        int userid = createCommentRequest.getUserId();
        int artworkId = createCommentRequest.getArtworkId();
        var user = userRepo.findUserByUsersID(userid).orElse(null);
        var artwork = artworkRepo.findByArtworkId(artworkId).orElse(null);
        if (user != null &&(user.getRole()== Role.AUDIENCE || user.getRole()==Role.CREATOR)) {
            if (artwork != null) {
                Comment comment = new Comment();
                comment.setCommentText(createCommentRequest.getCommentText());
                comment.setCommentedAt(new Date());
                comment.setUser(user);
                Comment savedComment = commentRepository.save(comment);
                return ResponseEntity.ok(new
                        ResponseObject(
                        "Success",
                        "Create comment success",
                        savedComment));

            }else {
                return ResponseEntity.ok(new
                        ResponseObject(
                        "Fail",
                        "Create comment fail",
                        null));

            }

        }else {
            return ResponseEntity.ok(new
                    ResponseObject(
                    "Fail",
                    "User not found",
                    null));
        }

    }

    @Override
    public ResponseEntity<ResponseObject> updateComment(Integer commentId, CreateCommentRequest createCommentRequest) {
        try {
            Comment existingComment = commentRepository.findById(commentId).orElse(null);
            if (existingComment == null) {
                // Xử lý trường hợp không tìm thấy blog
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
                // Xử lý trường hợp không tìm thấy blog
            }
            commentRepository.delete(comment);
            return ResponseEntity.ok(new ResponseObject("Success", "Delete blog success", null));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new ResponseObject("Fail", "Internal Server Error", null));
        }
    }

    @Override
    public ResponseEntity<ResponseObject> findCommentById(Integer commentId) {
        try {
            Comment comment = commentRepository.findById(commentId).orElse(null);
            if (comment == null) {
                // Xử lý trường hợp không tìm thấy blog
            }
            return ResponseEntity.ok(new ResponseObject("Success", "Find blog success", comment));
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
}


