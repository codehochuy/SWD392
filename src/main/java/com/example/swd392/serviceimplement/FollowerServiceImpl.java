package com.example.swd392.serviceimplement;

import com.example.swd392.Request.FollowerRequest.CreateFollowerRequest;
import com.example.swd392.Request.FollowerRequest.UpdateFollowerRequest;
import com.example.swd392.Response.FollowerResponse.*;
import com.example.swd392.model.Follower;
import com.example.swd392.model.User;
import com.example.swd392.repository.FollowerRepository;

import com.example.swd392.repository.UserRepo;
import com.example.swd392.service.FollowerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FollowerServiceImpl implements FollowerService {

    private final FollowerRepository followerRepository;
    private final UserRepo userRepository;

    @Autowired
    public FollowerServiceImpl(FollowerRepository followerRepository, UserRepo userRepository) {
        this.followerRepository = followerRepository;
        this.userRepository = userRepository;
    }

    @Override
    public ResponseEntity<CreateFollowerResponse> createFollower(CreateFollowerRequest followerRequest) {
        try {
            // Kiểm tra các trường cần thiết
            if (followerRequest.getUserId() == 0 || followerRequest.getFollowerUserId() == 0) {
                return ResponseEntity.badRequest().body(new CreateFollowerResponse("Fail", "User id and follower user id are required", null));
            }

            // Lấy thông tin người dùng từ userId
            User user = userRepository.findById(followerRequest.getUserId()).orElse(null);
            User followerUser = userRepository.findById(followerRequest.getFollowerUserId()).orElse(null);
            if (user == null || followerUser == null) {
                return ResponseEntity.badRequest().body(new CreateFollowerResponse("Fail", "User or follower user not found", null));
            }

            // Kiểm tra xem đã tồn tại một mục theo dõi giống hệt nhau trong cơ sở dữ liệu hay không
            if (followerRepository.existsByUserAndFollowerUser(user, followerUser)) {
                return ResponseEntity.badRequest().body(new CreateFollowerResponse("Fail", "User already follows follower user", null));
            }

            // Tạo đối tượng Follower từ request
            Follower follower = new Follower();
            follower.setUser(user);
            follower.setFollowerUser(followerUser);

            // Lưu đối tượng vào cơ sở dữ liệu
            Follower savedFollower = followerRepository.save(follower);

            // Tạo response
            return ResponseEntity.ok(new CreateFollowerResponse("Success", "Create follower success", savedFollower));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new CreateFollowerResponse("Fail", "Internal Server Error", null));
        }
    }



    @Override
    public ResponseEntity<UpdateFollowerResponse> updateFollower(int followerId, UpdateFollowerRequest followerRequest) {
        try {
            Follower existingFollower = followerRepository.findById(followerId).orElse(null);
            if (existingFollower == null) {
                return ResponseEntity.badRequest().body(new UpdateFollowerResponse("Fail", "Follower not found", null));
            }

            // Lấy thông tin người dùng từ followerRequest
            User user = userRepository.findById(followerRequest.getUserId()).orElse(null);
            User followerUser = userRepository.findById(followerRequest.getFollowerUserId()).orElse(null);
            if (user == null || followerUser == null) {
                return ResponseEntity.badRequest().body(new UpdateFollowerResponse("Fail", "User or follower user not found", null));
            }

            // Cập nhật thông tin follower
            existingFollower.setUser(user);
            existingFollower.setFollowerUser(followerUser);

            // Lưu thay đổi vào cơ sở dữ liệu
            Follower updatedFollower = followerRepository.save(existingFollower);

            // Tạo response
            return ResponseEntity.ok(new UpdateFollowerResponse("Success", "Update follower success", updatedFollower));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new UpdateFollowerResponse("Fail", "Internal Server Error", null));
        }
    }

    @Override
    public ResponseEntity<DeleteFollowerResponse> deleteFollower(int followerId) {
        try {
            Follower follower = followerRepository.findById(followerId).orElse(null);
            if (follower == null) {
                return ResponseEntity.badRequest().body(new DeleteFollowerResponse("Fail", "Follower not found", null));
            }

            // Xóa follower từ cơ sở dữ liệu
            followerRepository.delete(follower);

            return ResponseEntity.ok(new DeleteFollowerResponse("Success", "Delete follower success", null));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new DeleteFollowerResponse("Fail", "Internal Server Error", null));
        }
    }

    @Override
    public ResponseEntity<FindFollowerResponse> findFollowerById(int followerId) {
        try {
            Follower follower = followerRepository.findById(followerId).orElse(null);
            if (follower == null) {
                return ResponseEntity.badRequest().body(new FindFollowerResponse("Fail", "Follower not found", null));
            }

            // Tạo response
            return ResponseEntity.ok(new FindFollowerResponse("Success", "Find follower success", follower));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new FindFollowerResponse("Fail", "Internal Server Error", null));
        }
    }

    @Override
    public ResponseEntity<ListFollowerResponse> findAllFollowers() {
        try {
            List<Follower> followers = followerRepository.findAll();
            if (followers.isEmpty()) {
                return ResponseEntity.ok(new ListFollowerResponse("Success", "List is empty", "listIsEmpty"));
            }
            return ResponseEntity.ok(new ListFollowerResponse("Success", "List followers", followers));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new ListFollowerResponse("Fail", "Internal Server Error", null));
        }
    }
}
