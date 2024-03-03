package com.example.swd392.repository;

import com.example.swd392.model.Follower;
import com.example.swd392.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FollowerRepository extends JpaRepository<Follower, Integer> {
    boolean existsByUserAndFollowerUser(User user, User followerUser);
    // Các phương thức tùy chỉnh nếu cần
}