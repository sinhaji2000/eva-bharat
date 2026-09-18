package com.example.ava_bharat.mapper;

import java.util.List;

import org.springframework.stereotype.Component;

import com.example.ava_bharat.dto.PlaylistItemDto;
import com.example.ava_bharat.dto.WindowPlaybackDto;
import com.example.ava_bharat.entity.MediaItem;
import com.example.ava_bharat.entity.PlaylistItem;
import com.example.ava_bharat.entity.Window;

@Component
public class PlaylistMapper {

    public PlaylistItemDto toDto(PlaylistItem item) {
        MediaItem media = item.getMediaItem();
        return PlaylistItemDto.builder()
                .id(item.getId())
                .position(item.getPosition())
                .durationSeconds(item.getDurationSeconds())
                .mediaId(media.getId())
                .mediaCode(media.getCode())
                .mediaName(media.getName())
                .mediaType(media.getMediaType())
                .mediaUrl(media.getMediaUrl())
                .build();
    }

    public WindowPlaybackDto toWindowPlayback(Window window, List<PlaylistItem> items) {
        List<PlaylistItemDto> playlist = items.stream().map(this::toDto).toList();
        int playlistDuration = items.stream()
                .mapToInt(PlaylistItem::getDurationSeconds)
                .sum();
        return WindowPlaybackDto.builder()
                .id(window.getId())
                .name(window.getName())
                .cycleDurationSeconds(window.getCycleDurationSeconds())
                .playlistDurationSeconds(playlistDuration)
                .playlist(playlist)
                .build();
    }
}
