package com.example.swd392.controller;

import com.example.swd392.Request.CommentRequest.CreateCommentRequest;
import com.example.swd392.Response.ObjectResponse.ResponseObject;
import com.example.swd392.model.Comment;
import com.example.swd392.service.ArtworkService;
import com.example.swd392.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/comments")
public class ManagementController {

    private final CommentService commentService;

    @Autowired
    public ManagementController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/createComment")
    public ResponseEntity<ResponseObject> createComment(@RequestBody CreateCommentRequest createCommentRequest) {
        return commentService.createComment(createCommentRequest);

    }

    @PutMapping("/update/{commentId}")
    public ResponseEntity<ResponseObject> updateComment(
            @PathVariable Integer commentId,
            @RequestBody CreateCommentRequest createCommentRequest) {
      return commentService.updateComment(commentId, createCommentRequest);

    }

    @DeleteMapping("/delete/{commentId}")
    public ResponseEntity<ResponseObject> deleteComment(@PathVariable Integer commentId) {
        return commentService.deleteComment(commentId);

    }

    @GetMapping("/find/{commentId}")
    public ResponseEntity<ResponseObject> findCommentById(@PathVariable Integer commentId) {
        return commentService.findCommentById(commentId);

    }

    @GetMapping("/getall")
    public ResponseEntity<List<Comment>> getAllComments() {
        List<Comment> comments = commentService.getAllComments();
        return ResponseEntity.ok(comments);
    }

    @GetMapping("/comments/searchFilter")
    public ResponseEntity<?> searchCommentsFilter(
            @RequestParam(name = "commentText", required = false) String commentText,
            @RequestParam(name = "commentedAt", required = false) Date commentedAt
    ) {
        List<Comment> commentList = commentService.searchCommentsFilter(commentText, commentedAt);
        return ResponseEntity.ok(commentList);
    }
}
