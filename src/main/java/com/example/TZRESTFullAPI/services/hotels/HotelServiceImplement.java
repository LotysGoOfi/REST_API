package com.example.TZRESTFullAPI.services.hotels;

import com.example.TZRESTFullAPI.dtos.hotels.HotelResponse;
import com.example.TZRESTFullAPI.dtos.hotels.HotelSummaryResponse;
import com.example.TZRESTFullAPI.entities.Hotel;
import com.example.TZRESTFullAPI.repository.HotelsRepository;
import com.example.TZRESTFullAPI.transformers.HotelTransformer;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class HotelServiceImplement implements HotelService {

    private final HotelsRepository hotelsRepository;
    private final HotelTransformer hotelTransformer;

    @Override
    public HotelResponse findById(UUID uuid) {
        Hotel hotel = hotelsRepository
                .findById(uuid)
                .orElseThrow(
                        () -> new NoSuchElementException("Hotel with id " + uuid + " not found"));
        return hotelTransformer.toHotelResponse(hotel);
    }


    @Transactional
    public Hotel update(Hotel request) {
        if(hotelsRepository.existsById(request.getId())){
            return hotelsRepository.save(request);
        }
        return request;
    }


    public HotelResponse delete(UUID uuid) {
        Hotel hotel = hotelsRepository
                .findById(uuid)
                .orElseThrow(
                        () -> new NoSuchElementException("Hotel with id " + uuid + " not found"));
        hotelsRepository.delete(hotel);
        return hotelTransformer.toHotelResponse(hotel);
    }


    public Hotel create(Hotel request) {
        return hotelsRepository.save(request);
    }

    @Override
    public List<HotelSummaryResponse> findByAllSummary() {
        return hotelTransformer.toListHotelSummaryResponse(hotelsRepository.findAll());
    }

}
