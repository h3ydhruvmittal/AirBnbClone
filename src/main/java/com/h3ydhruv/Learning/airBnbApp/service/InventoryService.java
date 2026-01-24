package com.h3ydhruv.Learning.airBnbApp.service;

import com.h3ydhruv.Learning.airBnbApp.entity.Room;

public interface InventoryService {
    void initializeRoomForAYear(Room room);

    void deleteFutureInventories(Room room);
}
