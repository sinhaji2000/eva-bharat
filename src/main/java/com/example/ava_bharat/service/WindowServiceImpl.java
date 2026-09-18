package com.example.ava_bharat.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.ava_bharat.dto.CreateWindowRequesDto;
import com.example.ava_bharat.dto.CreateWindowResponseDto;
import com.example.ava_bharat.entity.Window;
import com.example.ava_bharat.mapper.WindowMapper;
import com.example.ava_bharat.repository.WindowRepository;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor 
@Data 
@Service 
public class WindowServiceImpl implements WindowService{
    
    private final WindowRepository windowRepository ;
    private final WindowMapper windowMapper ;

    public ResponseEntity<CreateWindowResponseDto> createWindow(CreateWindowRequesDto createWindowRequesDto){

        Window window = windowMapper.toEnitytWindow(createWindowRequesDto);

        Window savedWindow = windowRepository.save(window);

        CreateWindowResponseDto response =
                windowMapper.toWindowResponseDto(savedWindow);

        return ResponseEntity.ok(response);
    }
}
