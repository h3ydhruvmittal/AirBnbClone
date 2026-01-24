package com.h3ydhruv.Learning.airBnbApp.repository;

import com.h3ydhruv.Learning.airBnbApp.entity.Inventory;
import com.h3ydhruv.Learning.airBnbApp.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Long> {
    void deleteByDateAfterAndRoom(LocalDate date, Room room);
}
