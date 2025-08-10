package com.example.TZRESTFullAPI.repository;

import com.example.TZRESTFullAPI.entities.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface HotelsRepository extends JpaRepository<Hotel, UUID> {

    List<Hotel> findByBrandContainingIgnoreCase(String brand);
    List<Hotel> findByAddressCityContainingIgnoreCase(String city);
    List<Hotel> findByAddressCountryContainingIgnoreCase(String country);
    List<Hotel> findByAmenitiesContaining(String amenity);

    @Query("SELECT DISTINCT h FROM Hotel h WHERE " +
            "(:#{#name} IS NULL OR LOWER(h.name) LIKE LOWER(CONCAT('%', :#{#name}, '%'))) AND " +
            "(:#{#brand} IS NULL OR LOWER(h.brand) LIKE LOWER(CONCAT('%', :#{#brand}, '%'))) AND " +
            "(:#{#city} IS NULL OR LOWER(h.address.city) LIKE LOWER(CONCAT('%', :#{#city}, '%'))) AND " +
            "(:#{#country} IS NULL OR LOWER(h.address.country) LIKE LOWER(CONCAT('%', :#{#country}, '%'))) AND " +
            "(:#{#amenity} IS NULL OR EXISTS (SELECT 1 FROM h.amenities a WHERE a = :#{#amenity}))")
    List<Hotel> searchHotels(
            @Param("name") String name,
            @Param("brand") String brand,
            @Param("city") String city,
            @Param("country") String country,
            @Param("amenity") String amenity
    );

}
