package com.example.ava_bharat.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.ava_bharat.dto.SyncStateDto;
import com.example.ava_bharat.dto.TriggerSyncRequestDto;
import com.example.ava_bharat.service.SyncService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/sync")
@RequiredArgsConstructor
public class SyncController {

    private final SyncService syncService;

    @PostMapping
    public ResponseEntity<SyncStateDto> trigger(@RequestBody TriggerSyncRequestDto request) {
        return ResponseEntity.ok(syncService.triggerSync(request));
    }

    @GetMapping
    public ResponseEntity<SyncStateDto> current() {
        return ResponseEntity.ok(syncService.currentSync());
    }
}
