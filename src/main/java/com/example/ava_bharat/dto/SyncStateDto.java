package com.example.ava_bharat.dto;

import java.time.Instant;

import com.example.ava_bharat.entity.MediaType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SyncStateDto {

    private boolean active;
    private Long mediaId;
    private String mediaCode;
    private String mediaName;
    private MediaType mediaType;
    private String mediaUrl;
    private Integer durationSeconds;
    private Instant startedAt;
    private Instant endsAt;
    private Long remainingSeconds;
}
