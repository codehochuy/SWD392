package com.example.swd392.repository;

import com.example.swd392.model.Artwork;
import com.example.swd392.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ArtworkRepo extends JpaRepository<Artwork,Integer> {
    Optional<Artwork> findByArtworkId(int artworkID);
    Optional<Artwork> findArtworkByArtworkId(int id);
    @Query("SELECT a FROM Artwork a WHERE " +
            "(:artworkName IS NULL OR a.artworkName LIKE %:artworkName%) AND " +
            "(:price IS NULL OR a.price = :price)")
    List<Artwork> findArtworksByFilter(String artworkName, Double price);

    List<Artwork> findByUser(User user);

    @Query("SELECT DISTINCT ad.artwork FROM OrderDetail ad")
    List<Artwork> findAllArtworkInOrderDetails();

    @Query(value = "SELECT a.artworkid AS artworkId, " +
            "a.artwork_name AS artworkName, " +
            "a.artwork_url AS artworkUrl, " +
            "a.price, " +
            "a.like_count AS likeCount, " +
            "a.comment_count AS commentCount, " +
            "a.buy_count AS buyCount, " +
            "a.usersid AS userId, " +
            "od.OrderdetailID AS orderDetailId " +
            "FROM Artwork a " +
            "JOIN OrderDetail od ON a.ArtworkID = od.ArtworkID " +
            "WHERE a.ArtworkID = ?1", nativeQuery = true)
    List<Object[]> findOrderDetailsByArtworkID(int artworkID);

}

