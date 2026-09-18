package com.example.ava_bharat.mapper;

import org.springframework.stereotype.Component;

import com.example.ava_bharat.dto.CreateWindowRequesDto;
import com.example.ava_bharat.dto.CreateWindowResponseDto;
import com.example.ava_bharat.entity.Window;

@Component
public class WindowMapper {

    public Window toEnitytWindow(CreateWindowRequesDto createWindowRequesDto) {
        return Window.builder()
                .name(createWindowRequesDto.getName())
                .active(true)
                .cycleDurationSeconds(Window.FIVE_HOUR_CYCLE_SECONDS)
                .build();
    }

    public CreateWindowResponseDto toWindowResponseDto(Window window) {
        return CreateWindowResponseDto.builder()
                .id(window.getId())
                .name(window.getName())
                .cycleDurationSeconds(window.getCycleDurationSeconds())
                .build();
    }
}
