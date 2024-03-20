package com.example.swd392.controller;

import com.example.swd392.Request.ArtworkRequest.CreateArtworkRequest;
import com.example.swd392.Request.ArtworkRequest.UpdateArtworkRequest;
import com.example.swd392.Response.ArtworkResponse.CreateArtworkResponse;
import com.example.swd392.Response.ArtworkResponse.DeleteArtworkResponse;
import com.example.swd392.Response.ObjectResponse.ResponseObject;
import com.example.swd392.Response.UserResponse.ArtworkOrderDetailDTO;
import com.example.swd392.Response.UserResponse.BalanceAccountResponse;
import com.example.swd392.model.Artwork;
import com.example.swd392.model.OrderDetail;
import com.example.swd392.service.ArtworkService;
import com.example.swd392.service.OrderDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/creator")
@PreAuthorize("hasRole('CREATOR')")
public class CreatorController {
    @Autowired
    private ArtworkService artworkService;

    @Autowired
    private OrderDetailService detailService;
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

    @PostMapping("/create")
    @PreAuthorize("hasAuthority('creator:create')")
    public ResponseEntity<CreateArtworkResponse> createArtwork(@RequestBody CreateArtworkRequest request) {
        CreateArtworkResponse response = artworkService.createArtwork(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/artworks/{creatorId}")
    @PreAuthorize("hasAuthority('creator:read')")
    public ResponseEntity<List<Artwork>> getArtworksByCreatorId(@PathVariable int creatorId) {
        List<Artwork> artworks = artworkService.getArtWorkByCreatorId(creatorId);
        if (artworks != null && !artworks.isEmpty()) {
            return ResponseEntity.ok(artworks);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/historyBuyOf/{artworkID}")
    @PreAuthorize("hasAuthority('creator:read')")
    public List<ArtworkOrderDetailDTO> getOrderDetailsByArtworkID(@PathVariable int artworkID) {
        return artworkService.getOrderDetailsByArtworkID(artworkID);
    }

    @GetMapping("/history/{userId}")
    public ResponseEntity<List<OrderDetail>> getOrderDetailsByUser(@PathVariable int userId) {
        List<OrderDetail> orderDetails = detailService.getOrderDetailByUser(userId);
        if (!orderDetails.isEmpty()) {
            return new ResponseEntity<>(orderDetails, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }




}
