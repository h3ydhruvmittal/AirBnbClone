package com.h3ydhruv.Learning.airBnbApp.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;

import java.math.BigDecimal;

@JsonPropertyOrder({
        "id",
        "type",
        "basePrice",
        "photos",
        "amenities",
        "totalCount",
        "capacity"
})
@Data
public class RoomDto {
    private Long id;
    private String type;
    private BigDecimal basePrice;
    private String[] photos;
    private String[] amenities;
    private Integer totalCount;
    private Integer capacity;

}
