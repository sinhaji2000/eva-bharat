package com.example.ava_bharat.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.example.ava_bharat.dto.AddPlaylistItemRequestDto;
import com.example.ava_bharat.dto.CreateWindowRequesDto;
import com.example.ava_bharat.dto.CreateWindowResponseDto;
import com.example.ava_bharat.dto.PlaybackStateDto;
import com.example.ava_bharat.dto.PlaylistItemDto;
import com.example.ava_bharat.dto.WindowPlaybackDto;

public interface WindowService {

    ResponseEntity<CreateWindowResponseDto> createWindow(CreateWindowRequesDto createWindowRequesDto);

    List<WindowPlaybackDto> getWindows();

    PlaylistItemDto addMediaToWindow(Long windowId, AddPlaylistItemRequestDto request);

    PlaybackStateDto getPlaybackState();
}
