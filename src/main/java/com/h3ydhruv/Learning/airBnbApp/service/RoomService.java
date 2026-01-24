package com.h3ydhruv.Learning.airBnbApp.service;

import com.h3ydhruv.Learning.airBnbApp.dto.RoomDto;

import java.util.List;

public interface RoomService {
    RoomDto createNewRoom(Long hotelId, RoomDto roomDto);

    List<RoomDto> getAllRoomsInHotel(Long hotelId);

    RoomDto getRoomById(Long roomId);

    void deleteRoomById(Long roomId);


}
