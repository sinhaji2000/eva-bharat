package com.example.ava_bharat.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.ava_bharat.dto.CreateWindowRequesDto;
import com.example.ava_bharat.dto.CreateWindowResponseDto;
import com.example.ava_bharat.service.WindowService;

import lombok.RequiredArgsConstructor;

@RestController 
@RequestMapping("/api/window")
@RequiredArgsConstructor 
public class MediaController {
    
    private final WindowService windowService ;

    @PostMapping("/create")
    public ResponseEntity<CreateWindowResponseDto> createWindow(@RequestBody CreateWindowRequesDto createMediaRequesDto){

        return windowService.createWindow(createMediaRequesDto) ;
    }
}
