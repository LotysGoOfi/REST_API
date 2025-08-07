package com.example.TZRESTFullAPI.repository;

import com.example.TZRESTFullAPI.entities.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface HotelsRepository extends JpaRepository<Hotel, UUID> {
}
