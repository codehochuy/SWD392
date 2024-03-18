package com.example.swd392.service;

import com.example.swd392.Request.ArtworkRequest.CreateArtworkRequest;
import com.example.swd392.Request.ArtworkRequest.CreateArtworkRequest2;
import com.example.swd392.Request.ArtworkRequest.UpdateArtworkRequest;
import com.example.swd392.Request.UserRequest.CreatUserRequest;
import com.example.swd392.Response.ArtworkResponse.CreateArtworkResponse;
import com.example.swd392.Response.ArtworkResponse.DeleteArtworkResponse;
import com.example.swd392.Response.ObjectResponse.ResponseObject;
import com.example.swd392.Response.UserResponse.CreateUserResponse;
import com.example.swd392.Response.UserResponse.UpdateUserResponse;
import com.example.swd392.model.Artwork;
import com.example.swd392.model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ArtworkService {
    public List<Artwork> getListArtworkForGuest(int count);
    public CreateArtworkResponse createArtwork(CreateArtworkRequest request, MultipartFile file) throws IOException;
    public CreateArtworkResponse createArtwork3(CreateArtworkRequest2 request);
    DeleteArtworkResponse deleteArtwork(int artworkId);

    public CreateArtworkResponse updateArtwork(int artworkId,UpdateArtworkRequest request);

    ResponseEntity<ResponseObject> findArtworkId(Integer artworkId);

    List<Artwork> getAllArtworks();

    List<Artwork> findArtworksByFilter(String artworkName,  double price);
    public byte[] downloadImage(int fileName);

    public CreateArtworkResponse createArtwork2(CreateArtworkRequest request,MultipartFile file);

    List<Artwork> getArtWorkByCreatorId(int id);
}
