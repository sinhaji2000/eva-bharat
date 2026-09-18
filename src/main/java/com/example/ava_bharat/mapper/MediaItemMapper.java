package com.example.ava_bharat.mapper;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import com.example.ava_bharat.dto.CreateMediaItemRequestDto;
import com.example.ava_bharat.dto.CreateMediaItemResponseDto;
import com.example.ava_bharat.entity.MediaItem;
import com.example.ava_bharat.entity.MediaType;

@Component 
public class MediaItemMapper {
    

    public MediaItem toEntity(CreateMediaItemRequestDto createMediaItemRequestDto){

        return MediaItem.builder()
        .code(createMediaItemRequestDto.getCode())
        .name(createMediaItemRequestDto.getName())
        .active(true)
        .mediaType(createMediaItemRequestDto.getMediaType())
        .mediaUrl(createMediaItemRequestDto.getMediaUrl())
        .durationSeconds(createMediaItemRequestDto.getDurationSeconds())
        .createdAt(LocalDateTime.now())
        .updatedAt(LocalDateTime.now())

        .build() ;
    }

    public CreateMediaItemResponseDto toDto(MediaItem mediaItem){

        return CreateMediaItemResponseDto.builder()
        .id(mediaItem.getId())
        .code(mediaItem.getCode())
        .name(mediaItem.getName())
        .mediaType(mediaItem.getMediaType())
        .mediaUrl(mediaItem.getMediaUrl())
        .build() ;
    }
}
