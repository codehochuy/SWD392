package com.example.swd392.serviceimplement;

import com.example.swd392.Request.ArtworkRequest.CreateArtworkRequest;
import com.example.swd392.Response.ArtworkResponse.CreateArtworkResponse;
import com.example.swd392.Response.ArtworkResponse.DeleteArtworkResponse;
import com.example.swd392.Util.ImageUtil;
import com.example.swd392.enums.Role;
import com.example.swd392.model.Artwork;
import com.example.swd392.model.User;
import com.example.swd392.repository.ArtworkRepo;
import com.example.swd392.repository.UserRepo;
import com.example.swd392.service.ArtworkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
@Service
public class ArtworkServiceImplement implements ArtworkService {

    @Autowired
    private ArtworkRepo artworkRepo;
    @Autowired
    private UserRepo userRepo;
    @Override
    public List<Artwork> getListArtworkForGuest() {
        return artworkRepo.findAll();
    }

    @Override
    public CreateArtworkResponse createArtwork(CreateArtworkRequest request, MultipartFile file) throws IOException {
        String artworkName = request.getArtworkName();
        LocalDateTime postedAt = LocalDateTime.now();
        int creator = request.getCreator();
        var user = userRepo.findUserByUsersID(creator).orElse(null);
        if(user!= null && user.getRole() == Role.CREATOR){
            Artwork artwork = Artwork.builder()
                    .artworkName(artworkName)
                    .artworkUrl(ImageUtil.compressImage(file.getBytes()))
                    .user(user)
                    .commentCount(0)
                    .likeCount(0)
                    .postedAt(postedAt)
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
    public DeleteArtworkResponse deleteArtwork(int artworkId) {
        if (artworkRepo.existsById(artworkId)) {
            artworkRepo.deleteById(artworkId);
            return DeleteArtworkResponse.builder().status("Deleted ArtWork Successfully").build();
        } else {
            return DeleteArtworkResponse.builder().status("Artwork does not exist").build();
        }
    }

}
