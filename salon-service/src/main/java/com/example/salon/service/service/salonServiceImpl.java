package com.example.salon.service.service;

import com.example.salon.service.dto.salonDto;
import com.example.salon.service.dto.UserDto;
import com.example.salon.service.exception.SalonNotFoundException;
import com.example.salon.service.exception.UnauthorizedActionException;
import com.example.salon.service.model.Salon;
import com.example.salon.service.repository.salonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class salonServiceImpl implements salonService{

    private final salonRepository salonRepository;
    @Override
    public Salon createSalon(salonDto req, UserDto user) {
        Salon salon = new Salon();
        salon.setName(req.getName());
        salon.setEmail(req.getEmail());
        salon.setCity(req.getCity());
        salon.setAddress(req.getAddress());
        salon.setImages(req.getImages());
        salon.setOwnerId(req.getOwnerId());
        salon.setOpenTime(req.getOpenTime());
        salon.setCloseTime(req.getCloseTime());
        salon.setPhoneNumber(req.getPhoneNumber());

        return salonRepository.save(salon);
    }

    @Override
    public Salon updateSalon(salonDto salon, UserDto user, UUID salonId) {


        Salon existingSalon = salonRepository.findById(salonId)
                .orElseThrow(()-> new SalonNotFoundException(salonId));

        if (!existingSalon.getOwnerId().equals(user.getId())) {
            throw new UnauthorizedActionException("You do not have permission to update this salon");
        }

            existingSalon.setName(salon.getName());
            existingSalon.setEmail(salon.getEmail());
            existingSalon.setImages(salon.getImages());
            existingSalon.setCity(salon.getCity());
            existingSalon.setPhoneNumber(salon.getPhoneNumber());
            existingSalon.setAddress(salon.getAddress());
            existingSalon.setOpenTime(salon.getOpenTime());
            existingSalon.setCloseTime(salon.getCloseTime());
            return salonRepository.save(existingSalon);



    }

    @Override
    public List<Salon> getAllSalons() {
        return salonRepository.findAll();
    }

    @Override
    public Salon getSalonById(UUID salonId) {
       return salonRepository
               .findById(salonId)
               .orElseThrow(()-> new SalonNotFoundException(salonId));

    }

    @Override
    public Salon getSalonByOwnerId(UUID ownerId) {
        return salonRepository.findByOwnerId(ownerId)
                .orElseThrow(() -> new SalonNotFoundException("No salon found for owner id: " + ownerId));
    }

    @Override
    public List<Salon> searchSalonByCityName(String city) {
        return salonRepository.searchSalon(city);
    }
}
