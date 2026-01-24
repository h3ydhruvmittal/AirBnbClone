package com.h3ydhruv.Learning.airBnbApp.service;

import com.h3ydhruv.Learning.airBnbApp.dto.HotelDto;
import com.h3ydhruv.Learning.airBnbApp.entity.Hotel;

public interface HotelService {
    HotelDto createNewHotel(HotelDto hotelDto);

    HotelDto getHotelById(Long id);

    HotelDto updateHotelById(Long id, HotelDto hotelDto);

    void deleteHotelById(Long id);

    void activateHotel(Long hotelId);
}
