package com.h3ydhruv.Learning.airBnbApp.service;

import com.h3ydhruv.Learning.airBnbApp.dto.RoomDto;
import com.h3ydhruv.Learning.airBnbApp.entity.Hotel;
import com.h3ydhruv.Learning.airBnbApp.entity.Room;
import com.h3ydhruv.Learning.airBnbApp.exception.ResourceNotFoundException;
import com.h3ydhruv.Learning.airBnbApp.repository.HotelRepository;
import com.h3ydhruv.Learning.airBnbApp.repository.RoomRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class RoomServiceImpl implements RoomService{

    private final RoomRepository roomRepository;
    private final HotelRepository hotelRepository;
    private final ModelMapper modelMapper;
    private final InventoryService inventoryService;

    @Override
    public RoomDto createNewRoom(Long hotelId, RoomDto roomDto) {
        log.info("Creating a new Room in hotel with ID : {}", hotelId );
        Hotel hotel = hotelRepository
                .findById(hotelId)
                .orElseThrow(() -> new ResourceNotFoundException("Hotel didn't found with id: " + hotelId));
        Room room = modelMapper.map(roomDto, Room.class);
        room.setHotel(hotel);
        room = roomRepository.save(room);

        //Create inventory as soon as room is created and if hotel is active
        if(hotel.getActive()){
            inventoryService.initializeRoomForAYear(room);
        }

        return  modelMapper.map(room,RoomDto.class);
    }

    @Override
    public List<RoomDto> getAllRoomsInHotel(Long hotelId) {
        log.info("Getting all rooms in hotel with ID : {}", hotelId );
        Hotel hotel = hotelRepository
                .findById(hotelId)
                .orElseThrow(() -> new ResourceNotFoundException("Hotel didn't found with id: " + hotelId));

        return hotel.getRooms()
                .stream()
                .map((element) -> modelMapper.map(element, RoomDto.class))
                .collect(Collectors.toList());

    }

    @Override
    public RoomDto getRoomById(Long roomId) {
        log.info("Getting the room with ID : {}", roomId );
        Room room = roomRepository
                .findById(roomId)
                .orElseThrow(() -> new ResourceNotFoundException("Room didn't found with id: " + roomId));
        return modelMapper.map(room, RoomDto.class);
    }

    @Override
    @Transactional
    public void deleteRoomById(Long roomId) {
        log.info("Deleting the room with ID : {}", roomId );
        Room room = roomRepository
                .findById(roomId)
                .orElseThrow(() -> new ResourceNotFoundException("Room didn't found with id: " + roomId));

        //Delete all future inventory for this room first and then deleting the room
        log.info("Deleting the future inventory as well for the room with ID : {}", roomId );
        inventoryService.deleteFutureInventories(room);

        roomRepository.deleteById(roomId);

    }
}
