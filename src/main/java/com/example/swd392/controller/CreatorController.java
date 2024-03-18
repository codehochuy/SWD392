package com.example.swd392.controller;

import com.example.swd392.Request.ArtworkRequest.CreateArtworkRequest;
import com.example.swd392.Request.ArtworkRequest.CreateArtworkRequest2;
import com.example.swd392.Request.ArtworkRequest.UpdateArtworkRequest;
import com.example.swd392.Response.ArtworkResponse.CreateArtworkResponse;
import com.example.swd392.Response.ArtworkResponse.DeleteArtworkResponse;
import com.example.swd392.Response.ObjectResponse.ResponseObject;
import com.example.swd392.model.Artwork;
import com.example.swd392.model.Comment;
import com.example.swd392.service.ArtworkService;
import com.example.swd392.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.List;

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
            @RequestParam("price") double price,
            @RequestParam("file") MultipartFile file) throws IOException {
        CreateArtworkRequest request = new CreateArtworkRequest();
        request.setArtworkName(artworkName);
        request.setCreator(creator);
        request.setPrice(price);
        CreateArtworkResponse response = artworkService.createArtwork(request, file);
        return ResponseEntity.ok(response);
    }


    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('creator:delete')")
    public DeleteArtworkResponse deleteArtwork(@PathVariable("id") int artworkId) {
        return artworkService.deleteArtwork(artworkId);
    }

    @PutMapping("/updateArtwork/{artworkId}")
    @PreAuthorize("hasAuthority('creator:update')")
    public ResponseEntity<CreateArtworkResponse> updateArtwork(
            @PathVariable int artworkId,
            @RequestBody UpdateArtworkRequest request) {
        CreateArtworkResponse response = artworkService.updateArtwork(artworkId, request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/find/{artworkId}")
    public ResponseEntity<ResponseObject> findArtworkId(@PathVariable Integer artworkId) {
        return artworkService.findArtworkId(artworkId);

    }

    @GetMapping("/getallArtWork")
    public ResponseEntity<List<Artwork>> getAllComments() {
        List<Artwork> artworks = artworkService.getAllArtworks();
        return ResponseEntity.ok(artworks);
    }

    @GetMapping("/artworks/search")
    public ResponseEntity<?> searchArtworks(
            @RequestParam(name = "artworkName", required = false) String artworkName,
            @RequestParam(name = "price", required = false) double price
    ) {
        List<Artwork> artworkList = artworkService.findArtworksByFilter(artworkName, price);
        return ResponseEntity.ok(artworkList);
    }
    @GetMapping("/{id}")
//    @PreAuthorize("hasAuthority('audience:read')")
    public ResponseEntity<?> downloadImage(@PathVariable int id){
        byte[] imageData = artworkService.downloadImage(id);
        if(imageData == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Avatar not found" + id);
        }
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.valueOf("image/png"))
                .body(imageData);
    }

    @PostMapping("/create3")
    @PreAuthorize("hasAuthority('creator:create')")
    public ResponseEntity<CreateArtworkResponse> createArtwork2(@RequestBody CreateArtworkRequest2 request) {
        CreateArtworkResponse response = artworkService.createArtwork3(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/create2")
    @PreAuthorize("hasAuthority('creator:create')")
    public ResponseEntity<CreateArtworkResponse> createArtwork2(
            @RequestParam("artworkName") String artworkName,
            @RequestParam("creator") int creator,
            @RequestParam("price") double price,
            @RequestParam("file") MultipartFile file) throws IOException {
        CreateArtworkRequest request = new CreateArtworkRequest();
        request.setArtworkName(artworkName);
        request.setCreator(creator);
        request.setPrice(price);
        CreateArtworkResponse response = artworkService.createArtwork2(request, file);
        return ResponseEntity.ok(response);
    }


}
