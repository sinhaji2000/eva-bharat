package com.example.ava_bharat.service;

import com.example.ava_bharat.dto.SyncStateDto;
import com.example.ava_bharat.dto.TriggerSyncRequestDto;

public interface SyncService {

    SyncStateDto triggerSync(TriggerSyncRequestDto request);

    SyncStateDto currentSync();
}
