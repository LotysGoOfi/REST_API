package com.example.TZRESTFullAPI.services.hotels;

import com.example.TZRESTFullAPI.entities.Hotel;
import com.example.TZRESTFullAPI.repository.HotelsRepository;
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

    @Override
    public Hotel findById(UUID uuid) {
       return hotelsRepository
               .findById(uuid)
               .orElseThrow(
                       ()->new NoSuchElementException("Hotel with id " + uuid + " not found"));
    }

    @Override
    @Transactional
    public Hotel update(Hotel request) {
        if(hotelsRepository.existsById(request.getId())){
            return hotelsRepository.save(request);
        }
        return request;
    }

    @Override
    public Hotel delete(UUID uuid) {
        var hotel = findById(uuid);
        hotelsRepository.delete(hotel);
        return hotel;
    }

    @Override
    public Hotel create(Hotel request) {
        return hotelsRepository.save(request);
    }

    @Override
    public List<Hotel> findByAll() {
        return hotelsRepository.findAll();
    }
}
