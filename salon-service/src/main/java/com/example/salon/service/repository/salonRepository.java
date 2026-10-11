package com.example.salon.service.repository;


import com.example.salon.service.model.Salon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface salonRepository extends JpaRepository<Salon, UUID> {

    Optional<Salon> findByOwnerId(UUID id);

    @Query("""
            SELECT s
            FROM Salon s
            WHERE LOWER(s.city) LIKE LOWER(CONCAT('%', :keyword, '%'))
               OR LOWER(s.name) LIKE LOWER(CONCAT('%', :keyword, '%'))
               OR LOWER(s.address) LIKE LOWER(CONCAT('%', :keyword, '%'))
            """)
    List<Salon> searchSalon(@Param("keyword") String keyword);
}
