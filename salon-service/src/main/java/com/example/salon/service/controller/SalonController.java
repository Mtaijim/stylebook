package com.example.salon.service.controller;

import com.example.salon.service.dto.salonDto;
import com.example.salon.service.dto.UserDto;
import com.example.salon.service.mapper.SalonMapper;
import com.example.salon.service.model.Salon;
import com.example.salon.service.service.salonService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/salons")
@RequiredArgsConstructor
public class SalonController {

    private final salonService salonService;

    private static final UUID HARDCODED_USER_ID = UUID.fromString("3fa85f64-5717-4562-b3fc-2c963f66afa6");

    private UserDto currentUser() {
        UserDto userDto = new UserDto();
        userDto.setId(HARDCODED_USER_ID);
        return userDto;
    }

    @PostMapping
    public ResponseEntity<salonDto> createSalon(
          @Valid @RequestBody salonDto salonDto){

        Salon salon = salonService.createSalon(salonDto,currentUser() );
        return ResponseEntity.ok(SalonMapper.mapToDto(salon));

    }
@PutMapping("/{salonId}")
    public ResponseEntity<salonDto> updateSalon(
            @PathVariable UUID salonId,
          @Valid  @RequestBody salonDto salonDto) throws Exception {

        Salon salon = salonService.updateSalon(salonDto,currentUser(),salonId);
    return ResponseEntity.ok(SalonMapper.mapToDto(salon));

    }

    @GetMapping()
    public ResponseEntity<List<salonDto>> getAllSalon(){
   List<Salon> salons = salonService.getAllSalons();
      List<salonDto> salonDtos = salons.stream().map((salon)->{
          salonDto salonDto  = SalonMapper.mapToDto(salon);
          return salonDto;
              }
              ).toList();
      return ResponseEntity.ok(salonDtos);
    }

    @GetMapping("/{salonId}")
    public  ResponseEntity<salonDto> getSalonById(@PathVariable UUID salonId) throws Exception {

        Salon salon = salonService.getSalonById(salonId);
        return ResponseEntity.ok(SalonMapper.mapToDto(salon));
    }

    @GetMapping("/search")
    public  ResponseEntity<List<salonDto> >searchSalon(
            @RequestParam String city
    ) throws Exception {
       List<Salon>  salons = salonService.searchSalonByCityName(city);
        List<salonDto> salonDtos = salons.stream().map((salon)->{
                    salonDto salonDto  = SalonMapper.mapToDto(salon);
                    return salonDto;
                }
        ).toList();
        return ResponseEntity.ok(salonDtos);
    }
    @GetMapping("/owner/{ownerId}")
    public  ResponseEntity<salonDto> getSalonByOwnerId(@PathVariable UUID ownerId) throws Exception {
        Salon salon = salonService.getSalonByOwnerId(ownerId);
        return ResponseEntity.ok(SalonMapper.mapToDto(salon));
    }
}
