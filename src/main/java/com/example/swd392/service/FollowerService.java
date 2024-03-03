package com.example.swd392.service;

import com.example.swd392.Request.FollowerRequest.CreateFollowerRequest;
import com.example.swd392.Request.FollowerRequest.UpdateFollowerRequest;
import com.example.swd392.Response.FollowerResponse.*;

import com.example.swd392.model.Follower;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface FollowerService {
    ResponseEntity<CreateFollowerResponse> createFollower(CreateFollowerRequest createFollowerRequest);




    ResponseEntity<DeleteFollowerResponse> deleteFollower(int followerId);

    ResponseEntity<FindFollowerResponse> findFollowerById(int followerId);

    ResponseEntity<ListFollowerResponse> findAllFollowers();

    ResponseEntity<CreateFollowerResponse> updateFollower(int followerId, CreateFollowerRequest followerRequest);


    List<Follower> searchFollowers(Integer userId, String accountName);
}