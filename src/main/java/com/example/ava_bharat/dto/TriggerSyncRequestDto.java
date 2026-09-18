package com.example.ava_bharat.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TriggerSyncRequestDto {

    private Long mediaId;
    private Integer durationSeconds;
}
