package com.example.salon.service.exception;

import java.util.UUID;

public class SalonNotFoundException extends RuntimeException {
    public SalonNotFoundException(UUID salonId){
        super("salon not found with this id"+ salonId);
    }

    public SalonNotFoundException(String message){
        super(message);
    }
}
