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

import java.util.List;
import java.util.Optional;

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
        } else if (user != null && (user.getRole() == Role.AUDIENCE || user.getRole() == Role.CREATOR)) {
            if (user.getUsersID() == artWork.getUser().getUsersID()) {
                return CartResponse.builder()
                        .status("You can not buy your artwork")
                        .build();

            } else {
                Optional<Cart> existingCart = cartRepo.findByUserAndArtwork(user, artWork);
                if (existingCart.isPresent()) {
                    return CartResponse.builder()
                            .status("This artwork is already in the cart.")
                            .build();
                } else {
                    // Nếu chưa tồn tại, thêm vào giỏ hàng
                    Cart cart = new Cart();
                    cart.setUser(user);
                    cart.setArtwork(artWork);
                    cartRepo.save(cart);

                    return CartResponse.builder()
                            .status("Add to cart successfully")
                            .build();
                }
            }


        } else {
            return CartResponse.builder()
                    .status("Add to cart fail")
                    .build();
        }

    }

    @Override
    public CartResponse removeCart(int cartId) {
        if (cartRepo.existsById(cartId)) {
            cartRepo.deleteById(cartId);
            return CartResponse.builder()
                    .status("Item removed from cart successfully").build();
        } else {
            return CartResponse.builder()
                    .status("Item does not exist").build();
        }

    }

    @Override
    public List<Cart> viewCartByUserId(int userId) {
        var user = userRepo.findUserByUsersID(userId).orElse(null);
        if (user != null && (user.getRole() == Role.AUDIENCE || user.getRole() == Role.CREATOR)) {
            return cartRepo.findByUser(user);
        } else {
            return null;
        }
    }
}
