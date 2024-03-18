package com.example.swd392.service;

import com.example.swd392.Request.CommentRequest.CreateCommentRequest;
import com.example.swd392.Response.ObjectResponse.ResponseObject;
import com.example.swd392.model.Comment;
import org.springframework.http.ResponseEntity;

import java.util.Date;
import java.util.List;

public interface CommentService {


    ResponseEntity<ResponseObject> createComment(CreateCommentRequest createCommentRequest);


    ResponseEntity<ResponseObject> updateComment(Integer commentId, CreateCommentRequest createCommentRequest);

    ResponseEntity<ResponseObject> deleteComment(Integer commentId);

    ResponseEntity<ResponseObject> findCommentById(Integer commentId);

    List<Comment> getAllComments();

    List<Comment> searchCommentsFilter(String commentText, Date commentedAt);

    List<Comment> getAllCommentsByArtworkId(int artworkId);

}
