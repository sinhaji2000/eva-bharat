package com.example.ava_bharat.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.example.ava_bharat.dto.CreateMediaItemRequestDto;
import com.example.ava_bharat.dto.CreateMediaItemResponseDto;

public interface MediaItemService {

    ResponseEntity<CreateMediaItemResponseDto> createMediaItem(CreateMediaItemRequestDto dto);

    List<CreateMediaItemResponseDto> getAllMedia();
}
