package com.h3ydhruv.Learning.airBnbApp.service;

import com.h3ydhruv.Learning.airBnbApp.dto.HotelDto;
import com.h3ydhruv.Learning.airBnbApp.entity.Hotel;
import com.h3ydhruv.Learning.airBnbApp.entity.Room;
import com.h3ydhruv.Learning.airBnbApp.exception.ResourceNotFoundException;
import com.h3ydhruv.Learning.airBnbApp.repository.HotelRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class HotelServiceImpl implements HotelService{

    private final HotelRepository hotelRepository;
    private final ModelMapper modelMapper;
    private final InventoryService inventoryService;

    @Override
    public HotelDto createNewHotel(HotelDto hotelDto) {
        log.info("Creating a new Hotel with name: {}", hotelDto.getName());
        Hotel hotel = modelMapper.map(hotelDto,Hotel.class);
        hotel.setActive(false);
        hotel = hotelRepository.save(hotel);
        log.info("Created a new Hotel with id: {}", hotelDto.getId());
        return modelMapper.map(hotel,HotelDto.class);
    }

    @Override
    public HotelDto getHotelById(Long id) {
        log.info("Getting the hotel with ID: {}", id);
        Hotel hotel = hotelRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Hotel didn't found with id: " + id));
        return modelMapper.map(hotel,HotelDto.class);
    }

    @Override
    public HotelDto updateHotelById(Long id, HotelDto hotelDto) {
        log.info("Updating the hotel with ID: {}", id);
        Hotel hotel = hotelRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Hotel didn't found with id: " + id));

        modelMapper.map(hotelDto,hotel);
        hotel.setId(id);
        hotel = hotelRepository.save(hotel);
        return modelMapper.map(hotel,HotelDto.class);
    }

    @Override
    @Transactional
    public void deleteHotelById(Long id) {
        boolean exists = hotelRepository.existsById(id);
        if(!exists) throw new ResourceNotFoundException("Hotel didn't found with id: " + id);
        hotelRepository.deleteById(id);
        //delete the future Inventories for this hotel
        Hotel hotel = hotelRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Hotel didn't found with id: " + id));
        for(Room room : hotel.getRooms()){
            inventoryService.deleteFutureInventories(room);
        }

    }

    @Override
    @Transactional
    public void activateHotel(Long hotelId) {
        log.info("Activating the hotel with ID: {}", hotelId);
        Hotel hotel = hotelRepository
                .findById(hotelId)
                .orElseThrow(() -> new ResourceNotFoundException("Hotel didn't found with id: " + hotelId));
        hotel.setActive(true);

        //Create inventory for all the rooms for this hotel
        //Assuming only do it once
        for(Room room : hotel.getRooms()){
            inventoryService.initializeRoomForAYear(room);
        }

    }
}
