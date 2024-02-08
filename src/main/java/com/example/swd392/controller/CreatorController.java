package com.example.swd392.controller;

import com.example.swd392.Request.ArtworkRequest.CreateArtworkRequest;
import com.example.swd392.Response.ArtworkResponse.CreateArtworkResponse;
import com.example.swd392.Response.ArtworkResponse.DeleteArtworkResponse;
import com.example.swd392.service.ArtworkService;
import com.example.swd392.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/creator")
@PreAuthorize("hasRole('CREATOR')")
public class CreatorController {
    @Autowired
    private ArtworkService artworkService;

    @PostMapping("/create")
    @PreAuthorize("hasAuthority('creator:create')")
    public ResponseEntity<CreateArtworkResponse> createArtwork(
            @RequestParam("artworkName") String artworkName,
            @RequestParam("creator") int creator,
            @RequestParam("file") MultipartFile file) throws IOException {
        CreateArtworkRequest request = new CreateArtworkRequest();
        request.setArtworkName(artworkName);
        request.setCreator(creator);
        CreateArtworkResponse response = artworkService.createArtwork(request, file);
        return ResponseEntity.ok(response);
    }


    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('creator:delete')")
    public DeleteArtworkResponse deleteArtwork(@PathVariable("id") int artworkId) {
        return artworkService.deleteArtwork(artworkId);
    }


}
