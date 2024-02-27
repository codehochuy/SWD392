package com.example.swd392.controller;

import com.example.swd392.Request.CommentRequest.CreateCommentRequest;
import com.example.swd392.model.Comment;
import com.example.swd392.service.ArtworkService;
import com.example.swd392.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/Management")

public class ManagementController {


    @Autowired
    private CommentService commentService;

    @PostMapping("/create")
    public ResponseEntity<Comment> createComment(@RequestBody CreateCommentRequest request) {
        Comment comment = commentService.createComment(request);
        return ResponseEntity.ok(comment);
    }

}
