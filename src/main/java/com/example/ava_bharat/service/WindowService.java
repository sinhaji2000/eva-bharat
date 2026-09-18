package com.example.ava_bharat.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.ava_bharat.dto.CreateWindowRequesDto;
import com.example.ava_bharat.dto.CreateWindowResponseDto;



public interface WindowService {
    
    public ResponseEntity<CreateWindowResponseDto> createWindow(CreateWindowRequesDto createWindowRequesDto) ;
}
