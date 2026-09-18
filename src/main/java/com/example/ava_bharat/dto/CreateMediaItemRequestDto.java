package com.example.ava_bharat.dto;

import com.example.ava_bharat.entity.MediaType;

import lombok.Builder;
import lombok.Data;

@Data 
@Builder 
public class CreateMediaItemRequestDto {
    

    private String code ;
    private String name ;
    private MediaType mediaType ;
    private String mediaUrl ;
    private Integer durationSeconds ;
    
}
