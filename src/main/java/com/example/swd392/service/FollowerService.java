package com.example.swd392.service;

import com.example.swd392.Request.FollowerRequest.CreateFollowerRequest;
import com.example.swd392.Request.FollowerRequest.UpdateFollowerRequest;
import com.example.swd392.Response.FollowerResponse.*;

import org.springframework.http.ResponseEntity;

import java.util.List;

public interface FollowerService {
    ResponseEntity<CreateFollowerResponse> createFollower(CreateFollowerRequest createFollowerRequest);


    ResponseEntity<UpdateFollowerResponse> updateFollower(int followerId, UpdateFollowerRequest followerRequest);

    ResponseEntity<DeleteFollowerResponse> deleteFollower(int followerId);

    ResponseEntity<FindFollowerResponse> findFollowerById(int followerId);

    ResponseEntity<ListFollowerResponse> findAllFollowers();
}