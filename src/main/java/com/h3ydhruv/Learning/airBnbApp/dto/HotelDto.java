package com.h3ydhruv.Learning.airBnbApp.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.h3ydhruv.Learning.airBnbApp.entity.HotelContactInfo;
import lombok.Data;


@JsonPropertyOrder({
        "id",
        "name",
        "city",
        "photos",
        "amenities",
        "contactInfo",
        "active"
})
@Data
public class HotelDto {
    private Long id;
    private String name;
    private String city;
    private String[] photos;
    private String[] amenities;
    private HotelContactInfo contactInfo;
    private Boolean active;
}
