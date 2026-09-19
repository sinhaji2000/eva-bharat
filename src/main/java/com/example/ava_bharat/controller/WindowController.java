package com.example.ava_bharat.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.ava_bharat.dto.AddPlaylistItemRequestDto;
import com.example.ava_bharat.dto.CreateWindowRequesDto;
import com.example.ava_bharat.dto.CreateWindowResponseDto;
import com.example.ava_bharat.dto.PlaybackStateDto;
import com.example.ava_bharat.dto.PlaylistItemDto;
import com.example.ava_bharat.dto.WindowPlaybackDto;
import com.example.ava_bharat.service.WindowService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/windows")
@RequiredArgsConstructor
public class WindowController {

    private final WindowService windowService;

    @PostMapping
    public ResponseEntity<CreateWindowResponseDto> createWindow(
            @RequestBody CreateWindowRequesDto createMediaRequesDto) {
        return windowService.createWindow(createMediaRequesDto);
    }

    @GetMapping
    public ResponseEntity<List<WindowPlaybackDto>> getWindows() {
        return ResponseEntity.ok(windowService.getWindows());
    }

    @GetMapping("/state")
    public ResponseEntity<PlaybackStateDto> playbackState() {
        return ResponseEntity.ok(windowService.getPlaybackState());
    }

    @PostMapping("/{windowId}/playlist")
    public ResponseEntity<PlaylistItemDto> addMedia(
            @PathVariable Long windowId,
            @RequestBody AddPlaylistItemRequestDto request) {
        return ResponseEntity.ok(windowService.addMediaToWindow(windowId, request));
    }

    @DeleteMapping("/{windowId}/playlist/{itemId}")
    public ResponseEntity<Void> removeMedia(@PathVariable Long windowId, @PathVariable Long itemId) {
        windowService.removeMediaFromWindow(windowId, itemId);
        return ResponseEntity.noContent().build();
    }
}
