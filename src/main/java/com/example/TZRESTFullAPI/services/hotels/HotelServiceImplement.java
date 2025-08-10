package com.example.TZRESTFullAPI.services.hotels;

import com.example.TZRESTFullAPI.dtos.hotels.HotelRequest;
import com.example.TZRESTFullAPI.dtos.hotels.HotelResponse;
import com.example.TZRESTFullAPI.dtos.hotels.HotelSummaryResponse;
import com.example.TZRESTFullAPI.entities.Hotel;
import com.example.TZRESTFullAPI.repository.HotelsRepository;
import com.example.TZRESTFullAPI.transformers.HotelTransformer;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HotelServiceImplement implements HotelService {

    private final HotelsRepository hotelsRepository;
    private final HotelTransformer hotelTransformer;

    @Override
    public HotelResponse findById(UUID uuid) {
        var hotel = hotelsRepository
                .findById(uuid)
                .orElseThrow(
                        () -> new NoSuchElementException("Hotel with id " + uuid + " not found"));
        return hotelTransformer.toHotelResponse(hotel);
    }


    @Override
    public HotelSummaryResponse create(HotelRequest request) {
        var hotel = hotelTransformer.toHotel(request);
        var save = hotelsRepository.save(hotel);
        return hotelTransformer.toHotelSummaryResponse(save);
    }

    @Override
    @Transactional
    public HotelResponse addAmenities(UUID ID, List<String> amenities) {
        var hotel = hotelsRepository.findById(ID)
                .orElseThrow(() -> new NoSuchElementException("Hotel with id " + ID + " not found"));

        var existingAmenities = new HashSet<>(hotel.getAmenities());

        amenities.forEach(amenity -> {
            if (!existingAmenities.contains(amenity)) {
                hotel.getAmenities().add(amenity);
            }
        });

        var updatedHotel = hotelsRepository.save(hotel);
        return hotelTransformer.toHotelResponse(updatedHotel);

    }

    @Override
    public List<HotelSummaryResponse> searchHotels(String name, String brand, String city, String country, String amenity) {

        var searchName = StringUtils.hasText(name) ? name : null;
        var searchBrand = StringUtils.hasText(brand) ? brand : null;
        var searchCity = StringUtils.hasText(city) ? city : null;
        var searchCountry = StringUtils.hasText(country) ? country : null;
        var searchAmenity = StringUtils.hasText(amenity) ? amenity : null;

        var hotels = hotelsRepository.searchHotels(
                searchName,
                searchBrand,
                searchCity,
                searchCountry,
                searchAmenity
        );

        return hotelTransformer.toListHotelSummaryResponse(hotels);
    }

    @Override
    public List<HotelSummaryResponse> findByAllSummary() {
        return hotelTransformer.toListHotelSummaryResponse(hotelsRepository.findAll());
    }

    @Override
    public Map<String, Long> getHistogram(String param) {
        return switch (param.toLowerCase()) {
            case "brand" -> getBrandHistogram();
            case "city" -> getCityHistogram();
            case "country" -> getCountryHistogram();
            case "amenities" -> getAmenitiesHistogram();
            default ->
                    throw new IllegalArgumentException("Invalid parameter. Supported: brand, city, country, amenities");
        };
    }

    private Map<String, Long> getBrandHistogram() {

        var allBrands = hotelsRepository.findAll().stream()
                .map(Hotel::getBrand)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());


        return allBrands.stream()
                .collect(Collectors.toMap(
                        brand -> brand,
                        brand -> (long) hotelsRepository.findByBrandContainingIgnoreCase(brand).size()
                ));
    }

    private Map<String, Long> getCityHistogram() {
        var allCities = hotelsRepository.findAll().stream()
                .map(h -> h.getAddress() != null ? h.getAddress().getCity() : null)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());

        return allCities.stream()
                .collect(Collectors.toMap(
                        city -> city,
                        city -> (long) hotelsRepository.findByAddressCityContainingIgnoreCase(city).size()
                ));
    }

    private Map<String, Long> getCountryHistogram() {
        var allCountries = hotelsRepository.findAll().stream()
                .map(h -> h.getAddress() != null ? h.getAddress().getCountry() : null)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());

        return allCountries.stream()
                .collect(Collectors.toMap(
                        country -> country,
                        country -> (long) hotelsRepository.findByAddressCountryContainingIgnoreCase(country).size()
                ));
    }

    private Map<String, Long> getAmenitiesHistogram() {
        var allAmenities = hotelsRepository.findAll().stream()
                .flatMap(h -> h.getAmenities().stream())
                .collect(Collectors.toSet());

        return allAmenities.stream()
                .collect(Collectors.toMap(
                        amenity -> amenity,
                        amenity -> (long) hotelsRepository.findByAmenitiesContaining(amenity).size()
                ));
    }
}
