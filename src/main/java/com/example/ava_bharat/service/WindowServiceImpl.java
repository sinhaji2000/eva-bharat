package com.example.ava_bharat.service;

import java.time.Instant;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.example.ava_bharat.dto.AddPlaylistItemRequestDto;
import com.example.ava_bharat.dto.CreateWindowRequesDto;
import com.example.ava_bharat.dto.CreateWindowResponseDto;
import com.example.ava_bharat.dto.PlaybackStateDto;
import com.example.ava_bharat.dto.PlaylistItemDto;
import com.example.ava_bharat.dto.WindowPlaybackDto;
import com.example.ava_bharat.entity.MediaItem;
import com.example.ava_bharat.entity.PlaylistItem;
import com.example.ava_bharat.entity.Window;
import com.example.ava_bharat.mapper.MediaItemMapper;
import com.example.ava_bharat.mapper.PlaylistMapper;
import com.example.ava_bharat.mapper.WindowMapper;
import com.example.ava_bharat.repository.MediaItemRepository;
import com.example.ava_bharat.repository.PlaylistItemRepository;
import com.example.ava_bharat.repository.WindowRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class WindowServiceImpl implements WindowService {

    private final WindowRepository windowRepository;
    private final WindowMapper windowMapper;
    private final PlaylistItemRepository playlistItemRepository;
    private final MediaItemRepository mediaItemRepository;
    private final PlaylistMapper playlistMapper;
    private final MediaItemMapper mediaItemMapper;
    private final SyncService syncService;

    @Override
    public ResponseEntity<CreateWindowResponseDto> createWindow(CreateWindowRequesDto createWindowRequesDto) {
        Window window = windowMapper.toEnitytWindow(createWindowRequesDto);
        Window savedWindow = windowRepository.save(window);
        return ResponseEntity.ok(windowMapper.toWindowResponseDto(savedWindow));
    }

    @Override
    @Transactional(readOnly = true)
    public List<WindowPlaybackDto> getWindows() {
        return windowRepository.findAll().stream()
                .map(window -> playlistMapper.toWindowPlayback(
                        window,
                        playlistItemRepository.findByWindowIdOrderByPositionAsc(window.getId())))
                .toList();
    }

    @Override
    @Transactional
    public PlaylistItemDto addMediaToWindow(Long windowId, AddPlaylistItemRequestDto request) {
        if (request.getMediaId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "mediaId is required");
        }
        Window window = windowRepository.findById(windowId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Window not found"));
        MediaItem media = mediaItemRepository.findById(request.getMediaId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Media not found"));

        int duration = request.getDurationSeconds() != null
                ? request.getDurationSeconds()
                : media.getDurationSeconds();
        if (duration <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Duration must be greater than 0");
        }

        int nextPosition = playlistItemRepository.findTopByWindowIdOrderByPositionDesc(windowId)
                .map(item -> item.getPosition() + 1)
                .orElse(0);

        PlaylistItem item = PlaylistItem.builder()
                .window(window)
                .mediaItem(media)
                .position(nextPosition)
                .durationSeconds(duration)
                .build();

        return playlistMapper.toDto(playlistItemRepository.save(item));
    }

    @Override
    @Transactional
    public void removeMediaFromWindow(Long windowId, Long playlistItemId) {
        PlaylistItem item = playlistItemRepository.findById(playlistItemId)
                .filter(found -> found.getWindow().getId().equals(windowId))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Playlist item not found"));
        playlistItemRepository.delete(item);
    }

    @Override
    @Transactional(readOnly = true)
    public PlaybackStateDto getPlaybackState() {
        return PlaybackStateDto.builder()
                .serverTime(Instant.now())
                .sync(syncService.currentSync())
                .windows(getWindows())
                .mediaLibrary(mediaItemRepository.findAll().stream().map(mediaItemMapper::toDto).toList())
                .build();
    }
}
