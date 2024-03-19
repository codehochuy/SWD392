package com.example.swd392.serviceimplement;


import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.example.swd392.Request.ArtworkRequest.CreateArtworkRequest;
import com.example.swd392.Request.ArtworkRequest.CreateArtworkRequest2;
import com.example.swd392.Request.ArtworkRequest.UpdateArtworkRequest;
import com.example.swd392.Response.ArtworkResponse.CreateArtworkResponse;
import com.example.swd392.Response.ArtworkResponse.DeleteArtworkResponse;
import com.example.swd392.Response.ObjectResponse.ResponseObject;
import com.example.swd392.Util.CustomMultipartFile;
import com.example.swd392.Util.ImageUtil;
import com.example.swd392.enums.Role;
import com.example.swd392.model.Artwork;
import com.example.swd392.model.Comment;
import com.example.swd392.model.User;
import com.example.swd392.repository.ArtworkRepo;
import com.example.swd392.repository.UserRepo;
import com.example.swd392.service.ArtworkService;
import jakarta.validation.constraints.NotNull;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;





@Service
public class ArtworkServiceImplement implements ArtworkService {

    @Autowired
    private ArtworkRepo artworkRepo;
    @Autowired
    private UserRepo userRepo;

    @Autowired
    private Cloudinary cloudinary;


    @Override
    public List<Artwork> getListArtworkForGuest(int count) {
        List<Artwork> allArtworks = artworkRepo.findAll();
        Collections.shuffle(allArtworks, new Random(System.currentTimeMillis()));
        return allArtworks.subList(0, Math.min(count, allArtworks.size()));
    }


    //base 64
//    @Override
//    public CreateArtworkResponse createArtwork(CreateArtworkRequest request, MultipartFile file) throws IOException {
//        String artworkName = request.getArtworkName();
//        LocalDateTime postedAt = LocalDateTime.now();
//        double price = request.getPrice();
//        int creator = request.getCreator();
//        //Check validate
//        if (artworkName == null || artworkName.isEmpty()) {
//            return CreateArtworkResponse.builder()
//                    .status("Artwork name cannot be empty")
//                    .artwork(null)
//                    .build();
//        } else if (artworkName.length() > 30) {
//            return CreateArtworkResponse.builder()
//                    .status("Artwork name must not exceed 30 characters")
//                    .artwork(null)
//                    .build();
//        } else if (price <= 0 || price > 100000000) {
//            return CreateArtworkResponse.builder()
//                    .status("Wrong value !")
//                    .artwork(null)
//                    .build();
//        }
//        var user = userRepo.findUserByUsersID(creator).orElse(null);
//        if (user != null && user.getRole() == Role.CREATOR) {
//            Artwork artwork = Artwork.builder()
//                    .artworkName(artworkName)
//                    .user(user)
//                    .price(price)
//                    .commentCount(0)
//                    .likeCount(0)
//                    .postedAt(postedAt)
//                    .build();
//            byte[] imageData = file.getBytes();
//            byte[] compressedImageData = ImageUtil.compressImage(imageData);
//            artwork.setArtworkUrl(compressedImageData);
//            artworkRepo.save(artwork);
//
//            return CreateArtworkResponse.builder()
//                    .status("Create Artwork Successfully")
//                    .artwork(artwork)
//                    .build();
//        } else {
//            return CreateArtworkResponse.builder()
//                    .status("Create Artwork Fail")
//                    .artwork(null)
//                    .build();
//        }
//        return  null;
//    }

//    @Override
//    public CreateArtworkResponse createArtwork2(CreateArtworkRequest request) {
//        String artworkName = request.getArtworkName();
//        String artworkUrl = request.getUrl();
//        LocalDateTime postedAt = LocalDateTime.now();
//        double price = request.getPrice();
//        int creator = request.getCreator();
//        //Check validate
//        if (artworkName == null || artworkName.isEmpty()) {
//            return CreateArtworkResponse.builder()
//                    .status("Artwork name cannot be empty")
//                    .artwork(null)
//                    .build();
//        } else if (artworkName.length() > 30) {
//            return CreateArtworkResponse.builder()
//                    .status("Artwork name must not exceed 30 characters")
//                    .artwork(null)
//                    .build();
//        } else if (price <= 0 || price > 100000000) {
//            return CreateArtworkResponse.builder()
//                    .status("Wrong value !")
//                    .artwork(null)
//                    .build();
//        }
//        var user = userRepo.findUserByUsersID(creator).orElse(null);
//        if (user != null && user.getRole() == Role.CREATOR) {
//            Artwork artwork = Artwork.builder()
//                    .artworkName(artworkName)
//                    .artworkUrl(artworkUrl)
//                    .user(user)
//                    .price(price)
//                    .commentCount(0)
//                    .likeCount(0)
//                    .postedAt(postedAt)
//                    .build();
//            artworkRepo.save(artwork);
//
//            return CreateArtworkResponse.builder()
//                    .status("Create Artwork Successfully")
//                    .artwork(artwork)
//                    .build();
//        } else {
//            return CreateArtworkResponse.builder()
//                    .status("Create Artwork Fail")
//                    .artwork(null)
//                    .build();
//        }
//    }


    @Override
    public DeleteArtworkResponse deleteArtwork(int artworkId) {
        if (artworkRepo.existsById(artworkId)) {
            artworkRepo.deleteById(artworkId);
            return DeleteArtworkResponse.builder().status("Deleted ArtWork Successfully").build();
        } else {
            return DeleteArtworkResponse.builder().status("Artwork does not exist").build();
        }
    }

    @Override
    public CreateArtworkResponse updateArtwork(int artworkId, UpdateArtworkRequest request) {
        String name = request.getArtworkName();
        double price = request.getPrice();
        if (name.length() > 30) {
            return CreateArtworkResponse.builder()
                    .status("Artwork name must not exceed 30 characters")
                    .artwork(null)
                    .build();
        }
        if (price <= 0 || price > 100000000) {
            return CreateArtworkResponse.builder()
                    .status("Wrong value for price!")
                    .artwork(null)
                    .build();
        }
        var artWork = artworkRepo.findByArtworkId(artworkId).orElse(null);
        if (artWork != null) {
            artWork.setArtworkName(name);
            artWork.setPrice(price);
            return CreateArtworkResponse.builder()
                    .status("Update ArtWork Successfully")
                    .artwork(artWork).build();
        } else {
            return CreateArtworkResponse.builder()
                    .status("Artwork does not exist")
                    .artwork(null)
                    .build();
        }
    }

    @Override
    public ResponseEntity<ResponseObject> findArtworkId(Integer artworkId) {
        try {
            Artwork artwork = artworkRepo.findById(artworkId).orElse(null);
            if (artwork == null) {

            }
            return ResponseEntity.ok(new ResponseObject("Success", "Find blog success", artwork));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new ResponseObject("Fail", "Internal Server Error", null));
        }
    }
    @Override
    public List<Artwork> getAllArtworks() {
        return artworkRepo.findAll();
    }

    @Override
    public List<Artwork> findArtworksByFilter(String artworkName,  double price) {
        return artworkRepo.findArtworksByFilter(artworkName, price);
    }

    @Override
    public byte[] downloadImage(int fileName) {
//        Artwork artwork = artworkRepo.findByArtworkId(fileName).orElse(null);
//        if (artwork == null || artwork.getArtworkUrl() == null) {
//            return null;
//        }
//        return ImageUtil.decompressImage(artwork.getArtworkUrl());
        return null;
    }
    public String uploadImageToCloudinary(MultipartFile file) {
        try {
            // Upload image to Cloudinary
            Map<String, Object> uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());

            // Get the URL of the uploaded image from Cloudinary response
            return (String) uploadResult.get("url");
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

//    @Override
//    public CreateArtworkResponse createArtwork2(CreateArtworkRequest request,MultipartFile file) {
//        String artworkName = request.getArtworkName();
////        String artworkUrl = request.getUrl();
//        LocalDateTime postedAt = LocalDateTime.now();
//        double price = request.getPrice();
//        int creator = request.getCreator();
//        //Check validate
//        if (artworkName == null || artworkName.isEmpty()) {
//            return CreateArtworkResponse.builder()
//                    .status("Artwork name cannot be empty")
//                    .artwork(null)
//                    .build();
//        } else if (artworkName.length() > 30) {
//            return CreateArtworkResponse.builder()
//                    .status("Artwork name must not exceed 30 characters")
//                    .artwork(null)
//                    .build();
//        } else if (price <= 0 || price > 100000000) {
//            return CreateArtworkResponse.builder()
//                    .status("Wrong value !")
//                    .artwork(null)
//                    .build();
//        }
//        var user = userRepo.findUserByUsersID(creator).orElse(null);
//        if (user != null && user.getRole() == Role.CREATOR) {
//            String artworkUrl = uploadImageToCloudinary(file);
//            Artwork artwork = Artwork.builder()
//                    .artworkName(artworkName)
//                    .artworkUrl(artworkUrl)
//                    .user(user)
//                    .price(price)
//                    .commentCount(0)
//                    .likeCount(0)
//                    .postedAt(postedAt)
//                    .build();
//            artworkRepo.save(artwork);
//
//            return CreateArtworkResponse.builder()
//                    .status("Create Artwork Successfully")
//                    .artwork(artwork)
//                    .build();
//        } else {
//            return CreateArtworkResponse.builder()
//                    .status("Create Artwork Fail")
//                    .artwork(null)
//                    .build();
//        }
//    }
private String extractBase64FromUrl(String url) {
    // Kiểm tra xem URL có chứa dấu phẩy "," không
    int commaIndex = url.indexOf(",");
    if (commaIndex != -1) {
        // Trích xuất phần base64 sau dấu phẩy
        return url.substring(commaIndex + 1);
    } else {
        // Nếu không tìm thấy dấu phẩy, trả về null hoặc xử lý theo cách phù hợp với ứng dụng của bạn
        return null;
    }
}
    @Override
public CreateArtworkResponse createArtwork(CreateArtworkRequest request) {
    String artworkName = request.getArtworkName();
    double price = request.getPrice();
    int creator = request.getCreator();
    String base64Url = extractBase64FromUrl(request.getUrl()); // Assuming url is base64 encoded

    // Convert base64 string to file
    byte[] decodedBytes = Base64.getDecoder().decode(base64Url);
    MultipartFile file = new CustomMultipartFile("file", "filename", MediaType.IMAGE_JPEG_VALUE, decodedBytes);

    // Check validation
    if (artworkName == null || artworkName.isEmpty()) {
        return CreateArtworkResponse.builder()
                .status("Artwork name cannot be empty")
                .artwork(null)
                .build();
    } else if (artworkName.length() > 30) {
        return CreateArtworkResponse.builder()
                .status("Artwork name must not exceed 30 characters")
                .artwork(null)
                .build();
    } else if (price <= 0 || price > 100000000) {
        return CreateArtworkResponse.builder()
                .status("Wrong value!")
                .artwork(null)
                .build();
    }
        if (base64Url == null || base64Url.isEmpty()) {
            return CreateArtworkResponse.builder()
                    .status("URL cannot be empty")
                    .artwork(null)
                    .build();
        }


        var user = userRepo.findUserByUsersID(creator).orElse(null);
    if (user != null ) {
//        && user.getRole() == Role.CREATOR
        String artworkUrl = uploadImageToCloudinary(file); // Assuming uploadImageToCloudinary function is defined elsewhere
        Artwork artwork = Artwork.builder()
                .artworkName(artworkName)
                .artworkUrl(artworkUrl)
                .user(user)
                .price(price)
                .commentCount(0)
                .likeCount(0)
                .postedAt(LocalDateTime.now())
                .build();
        artworkRepo.save(artwork);

        return CreateArtworkResponse.builder()
                .status("Create Artwork Successfully")
                .artwork(artwork)
                .build();
    } else {
        return CreateArtworkResponse.builder()
                .status("Create Artwork Fail")
                .artwork(null)
                .build();
    }
}



    @Override
    public List<Artwork> getArtWorkByCreatorId(int id) {
        var user = userRepo.findByUsersID(id).orElse(null);
        if (user != null) {
            return artworkRepo.findByUser(user);
        } else {
            return  null;
        }
    }

}
