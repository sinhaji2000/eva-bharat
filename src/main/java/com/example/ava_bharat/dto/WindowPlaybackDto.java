package com.example.ava_bharat.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WindowPlaybackDto {

    private Long id;
    private String name;
    private Integer cycleDurationSeconds;
    private Integer playlistDurationSeconds;
    private List<PlaylistItemDto> playlist;
}
