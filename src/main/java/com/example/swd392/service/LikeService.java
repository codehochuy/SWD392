package com.example.swd392.service;

import com.example.swd392.Request.LikeRequest.CreateLikeRequest;
import com.example.swd392.Request.LikeRequest.DeleteLikeRequest;
import com.example.swd392.Response.LikeResponse.CreateLikeResponse;
import com.example.swd392.Response.LikeResponse.DeleteLikeResponse;
import com.example.swd392.Response.LikeResponse.FindLikeResponse;
import com.example.swd392.Response.LikeResponse.ListLikeResponse;
import org.springframework.http.ResponseEntity;

public interface LikeService {
    ResponseEntity<CreateLikeResponse> createLike(CreateLikeRequest likeRequest);


    ResponseEntity<DeleteLikeResponse> deleteLike(int likeId);

    ResponseEntity<FindLikeResponse> findLikeById(int likeId);

    ResponseEntity<ListLikeResponse> findAllLikes();
}