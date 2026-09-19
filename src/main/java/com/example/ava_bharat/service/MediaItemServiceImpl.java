package com.example.ava_bharat.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.ava_bharat.dto.CreateMediaItemRequestDto;
import com.example.ava_bharat.dto.CreateMediaItemResponseDto;
import com.example.ava_bharat.entity.MediaItem;
import com.example.ava_bharat.entity.MediaType;
import com.example.ava_bharat.mapper.MediaItemMapper;
import com.example.ava_bharat.repository.MediaItemRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MediaItemServiceImpl implements MediaItemService {

    private final MediaItemRepository mediaItemRepository;
    private final MediaItemMapper mediaItemMapper;

    @Override
    public ResponseEntity<CreateMediaItemResponseDto> createMediaItem(CreateMediaItemRequestDto dto) {
        if (dto.getCode() == null || dto.getCode().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Media code is required");
        }
        if (mediaItemRepository.existsByCode(dto.getCode())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Media code already exists");
        }
        if (dto.getMediaType() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Media type is required");
        }
        if (dto.getMediaType() != MediaType.BLANK && (dto.getMediaUrl() == null || dto.getMediaUrl().isBlank())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Media URL is required for images and videos");
        }
        if (dto.getDurationSeconds() == null || dto.getDurationSeconds() <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Duration must be greater than 0");
        }

        MediaItem media = mediaItemMapper.toEntity(dto);
        MediaItem saved = mediaItemRepository.save(media);
        return ResponseEntity.ok(mediaItemMapper.toDto(saved));
    }

    @Override
    public List<CreateMediaItemResponseDto> getAllMedia() {
        return mediaItemRepository.findAll().stream()
                .map(mediaItemMapper::toDto)
                .toList();
    }
}
