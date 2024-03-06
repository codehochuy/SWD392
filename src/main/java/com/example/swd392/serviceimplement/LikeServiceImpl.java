package com.example.swd392.serviceimplement;

import com.example.swd392.Request.LikeRequest.CreateLikeRequest;
import com.example.swd392.Request.LikeRequest.DeleteLikeRequest;
import com.example.swd392.Response.FollowerResponse.DeleteFollowerResponse;
import com.example.swd392.Response.FollowerResponse.FindFollowerResponse;
import com.example.swd392.Response.FollowerResponse.ListFollowerResponse;
import com.example.swd392.Response.LikeResponse.CreateLikeResponse;
import com.example.swd392.Response.LikeResponse.DeleteLikeResponse;
import com.example.swd392.Response.LikeResponse.FindLikeResponse;
import com.example.swd392.Response.LikeResponse.ListLikeResponse;
import com.example.swd392.model.Artwork;
import com.example.swd392.model.Follower;
import com.example.swd392.model.Like;
import com.example.swd392.model.User;

import com.example.swd392.repository.ArtworkRepo;
import com.example.swd392.repository.LikeRepository;

import com.example.swd392.repository.UserRepo;
import com.example.swd392.service.LikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LikeServiceImpl implements LikeService {

    @Autowired
    private LikeRepository likeRepository;


   @Autowired
   private UserRepo userRepository;
   @Autowired
   private ArtworkRepo artworkRepository;

    @Override
    public ResponseEntity<CreateLikeResponse> createLike(CreateLikeRequest likeRequest) {
        try {
            // Kiểm tra các trường cần thiết
            if (likeRequest.getUserId() == 0 || likeRequest.getArtworkId() == 0) {
                return ResponseEntity.badRequest().body(new CreateLikeResponse("Fail", "User id and artwork id are required", null));
            }

            // Lấy thông tin người dùng và artwork
            User user = userRepository.findById(likeRequest.getUserId()).orElse(null);
            Artwork artwork = artworkRepository.findById(likeRequest.getArtworkId()).orElse(null);
            if (user == null || artwork == null) {
                return ResponseEntity.badRequest().body(new CreateLikeResponse("Fail", "User or artwork not found", null));
            }

            // Kiểm tra xem đã tồn tại một like giống nhau trong cơ sở dữ liệu hay không
            if (likeRepository.existsByUserAndArtwork(user, artwork)) {
                return ResponseEntity.badRequest().body(new CreateLikeResponse("Fail", "User already liked the artwork", null));
            }

            // Tạo đối tượng Like từ request
            Like like = new Like();
            like.setUser(user);
            like.setArtwork(artwork);

            // Lưu đối tượng vào cơ sở dữ liệu
            Like savedLike = likeRepository.save(like);

            // Tạo response
            return ResponseEntity.ok(new CreateLikeResponse("Success", "Create like success", savedLike));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new CreateLikeResponse("Fail", "Internal Server Error", null));
        }
    }

    @Override
    public ResponseEntity<DeleteLikeResponse> deleteLike(int likeId) {
        try {
            Like like = likeRepository.findById(likeId).orElse(null);
            if (like == null) {
                return ResponseEntity.badRequest().body(new DeleteLikeResponse("Fail", "Follower not found", null));
            }

            // Xóa follower từ cơ sở dữ liệu
            likeRepository.delete(like);

            return ResponseEntity.ok(new DeleteLikeResponse("Success", "Delete follower success", null));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new DeleteLikeResponse("Fail", "Internal Server Error", null));
        }
    }

    @Override
    public ResponseEntity<FindLikeResponse> findLikeById(int likeId) {
        try {
            Like like = likeRepository.findById(likeId).orElse(null);
            if (like == null) {
                return ResponseEntity.badRequest().body(new FindLikeResponse("Fail", "Follower not found", null));
            }

            // Tạo response
            return ResponseEntity.ok(new FindLikeResponse("Success", "Find follower success", like));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new FindLikeResponse("Fail", "Internal Server Error", null));
        }
    }

    @Override
    public ResponseEntity<ListLikeResponse> findAllLikes() {
        try {
            List<Like> likes = likeRepository.findAll();
            if (likes.isEmpty()) {
                return ResponseEntity.ok(new ListLikeResponse("Success", "List is empty", "listIsEmpty"));
            }
            return ResponseEntity.ok(new ListLikeResponse("Success", "List followers", likes));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new ListLikeResponse("Fail", "Internal Server Error", null));
        }
    }

    @Override
    public ResponseEntity<ListLikeResponse> findAllLikesPageNable(Pageable pageable) {
        try {
            Page<Like> likePage = likeRepository.findAll(pageable);
            List<Like> likes = likePage.getContent();

            if (likes.isEmpty()) {
                return ResponseEntity.ok(new ListLikeResponse("Success", "List is empty", "listIsEmpty"));
            }

            return ResponseEntity.ok(new ListLikeResponse("Success", "List followers", likes));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new ListLikeResponse("Fail", "Internal Server Error", null));
        }
    }


}