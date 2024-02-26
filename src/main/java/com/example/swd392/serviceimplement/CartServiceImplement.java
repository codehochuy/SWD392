package com.example.swd392.serviceimplement;

import com.example.swd392.Request.CartRequest.AddToCartRequest;
import com.example.swd392.Response.CartResponse.CartResponse;
import com.example.swd392.enums.Role;
import com.example.swd392.model.Artwork;
import com.example.swd392.model.Cart;
import com.example.swd392.model.User;
import com.example.swd392.repository.ArtworkRepo;
import com.example.swd392.repository.CartRepo;
import com.example.swd392.repository.UserRepo;
import com.example.swd392.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartServiceImplement implements CartService {

    @Autowired
    private CartRepo cartRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private ArtworkRepo artworkRepo;


    public CartResponse addToCart(AddToCartRequest request) {
        int userId = request.getUserId();
        int artWorkId = request.getArtworkId();

        var user = userRepo.findUserByUsersID(userId).orElse(null);
        var artWork = artworkRepo.findByArtworkId(artWorkId).orElse(null);
        if (artWork == null) {
            return CartResponse.builder()
                    .status("Artwork not found !")
                    .build();
        }
        else if(user != null && (user.getRole()== Role.AUDIENCE || user.getRole()==Role.CREATOR)){
            Cart cart = new Cart();
            cart.setUser(user);
            cart.setArtwork(artWork);
            cartRepo.save(cart);

            return CartResponse.builder()
                    .status("Add to cart successfully")
                    .build();

        }else {
            return CartResponse.builder()
                    .status("Add to cart fail")
                    .build();
        }

    }
}
