package com.example.swd392.serviceimplement;

import com.example.swd392.Request.ArtworkRequest.CreateArtworkRequest;
import com.example.swd392.Response.ArtworkResponse.CreateArtworkResponse;
import com.example.swd392.Util.ImageUtil;
import com.example.swd392.model.Artwork;
import com.example.swd392.model.User;
import com.example.swd392.repository.ArtworkRepo;
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
    @Override
    public List<Artwork> getListArtworkForGuest() {
        return artworkRepo.findAll();
    }

    @Override
    public CreateArtworkResponse createArtwork(CreateArtworkRequest request, MultipartFile file) throws IOException {
        String ArtworkName = request.getArtworkName();
        LocalDateTime postedAt = LocalDateTime.now();
        User user = request.getCreator();
        Artwork artwork = Artwork.builder()
                .artworkName(ArtworkName)
                .artworkUrl(ImageUtil.compressImage(file.getBytes()))
                .user(user)
                .commentCount(0)
                .likeCount(0)
                .postedAt(postedAt)
                .build();
        artworkRepo.save(artwork);

        return null;
    }
}
