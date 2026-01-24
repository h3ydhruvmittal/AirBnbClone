package com.h3ydhruv.Learning.airBnbApp.repository;

import com.h3ydhruv.Learning.airBnbApp.entity.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HotelRepository extends JpaRepository<Hotel,Long> {

}
