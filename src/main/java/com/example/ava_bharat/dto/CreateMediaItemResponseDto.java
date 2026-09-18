package com.example.ava_bharat.dto;

import com.example.ava_bharat.entity.MediaType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateMediaItemResponseDto {

    private Long id;
    private String code;
    private String name;
    private MediaType mediaType;
    private String mediaUrl;
    private Integer durationSeconds;
}
