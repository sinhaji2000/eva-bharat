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
public class PlaylistItemDto {

    private Long id;
    private Integer position;
    private Integer durationSeconds;
    private Long mediaId;
    private String mediaCode;
    private String mediaName;
    private MediaType mediaType;
    private String mediaUrl;
}
