package com.example.ava_bharat.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.ava_bharat.dto.CreateMediaItemRequestDto;
import com.example.ava_bharat.dto.CreateMediaItemResponseDto;
import com.example.ava_bharat.service.MediaItemService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/media")
@RequiredArgsConstructor
public class MediaItemController {

    private final MediaItemService mediaItemService;

    @PostMapping
    public ResponseEntity<CreateMediaItemResponseDto> createMediaItem(
            @RequestBody CreateMediaItemRequestDto createMediaItemRequestDto) {
        return mediaItemService.createMediaItem(createMediaItemRequestDto);
    }

    @GetMapping
    public ResponseEntity<List<CreateMediaItemResponseDto>> getAllMedia() {
        return ResponseEntity.ok(mediaItemService.getAllMedia());
    }
}
