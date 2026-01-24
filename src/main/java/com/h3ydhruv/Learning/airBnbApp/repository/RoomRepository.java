package com.h3ydhruv.Learning.airBnbApp.repository;

import com.h3ydhruv.Learning.airBnbApp.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {
}
