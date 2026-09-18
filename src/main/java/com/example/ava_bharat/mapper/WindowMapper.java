package com.example.ava_bharat.mapper;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import com.example.ava_bharat.dto.CreateWindowRequesDto;
import com.example.ava_bharat.dto.CreateWindowResponseDto;
import com.example.ava_bharat.entity.Window;




@Component 
public class WindowMapper {
    

    public Window toEnitytWindow(CreateWindowRequesDto createWindowRequesDto){

        return Window.builder()
        .name(createWindowRequesDto.getName())
        .createdAt(LocalDateTime.now())
        .active(true)
        .cycleDurationSeconds(800000)
        .updatedAt(LocalDateTime.now())
        .build() ;
    }

    public CreateWindowResponseDto toWindowResponseDto(Window window){

        return CreateWindowResponseDto.builder()
        .id(window.getId())
        .name(window.getName())
        .build() ;
    }
}
