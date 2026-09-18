package com.example.ava_bharat.mapper;

import org.springframework.stereotype.Component;

import com.example.ava_bharat.dto.CreateMediaItemRequestDto;
import com.example.ava_bharat.dto.CreateMediaItemResponseDto;
import com.example.ava_bharat.entity.MediaItem;

@Component
public class MediaItemMapper {

    public MediaItem toEntity(CreateMediaItemRequestDto dto) {
        return MediaItem.builder()
                .code(dto.getCode())
                .name(dto.getName())
                .active(true)
                .mediaType(dto.getMediaType())
                .mediaUrl(dto.getMediaUrl())
                .durationSeconds(dto.getDurationSeconds())
                .build();
    }

    public CreateMediaItemResponseDto toDto(MediaItem mediaItem) {
        return CreateMediaItemResponseDto.builder()
                .id(mediaItem.getId())
                .code(mediaItem.getCode())
                .name(mediaItem.getName())
                .mediaType(mediaItem.getMediaType())
                .mediaUrl(mediaItem.getMediaUrl())
                .durationSeconds(mediaItem.getDurationSeconds())
                .build();
    }
}
