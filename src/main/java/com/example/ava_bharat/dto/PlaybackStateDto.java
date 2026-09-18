package com.example.ava_bharat.dto;

import java.time.Instant;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PlaybackStateDto {

    private Instant serverTime;
    private SyncStateDto sync;
    private List<WindowPlaybackDto> windows;
    private List<CreateMediaItemResponseDto> mediaLibrary;
}
