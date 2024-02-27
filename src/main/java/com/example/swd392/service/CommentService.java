package com.example.swd392.service;

import com.example.swd392.Request.CommentRequest.CreateCommentRequest;
import com.example.swd392.model.Comment;

public interface CommentService {
    Comment createComment(CreateCommentRequest request);
}
