package com.example.ava_bharat.service;

import java.time.Duration;
import java.time.Instant;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.example.ava_bharat.dto.SyncStateDto;
import com.example.ava_bharat.dto.TriggerSyncRequestDto;
import com.example.ava_bharat.entity.MediaItem;
import com.example.ava_bharat.entity.SyncEvent;
import com.example.ava_bharat.repository.MediaItemRepository;
import com.example.ava_bharat.repository.SyncEventRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SyncServiceImpl implements SyncService {

    private final SyncEventRepository syncEventRepository;
    private final MediaItemRepository mediaItemRepository;

    @Override
    @Transactional
    public SyncStateDto triggerSync(TriggerSyncRequestDto request) {
        if (request.getMediaId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "mediaId is required");
        }
        int duration = request.getDurationSeconds() != null ? request.getDurationSeconds() : 10;
        if (duration <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Sync duration must be greater than 0");
        }

        MediaItem media = mediaItemRepository.findById(request.getMediaId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Media not found"));

        List<SyncEvent> activeEvents = syncEventRepository.findAll().stream()
                .filter(event -> Boolean.TRUE.equals(event.getActive()))
                .toList();
        activeEvents.forEach(event -> event.setActive(false));
        syncEventRepository.saveAll(activeEvents);

        SyncEvent event = SyncEvent.builder()
                .mediaItem(media)
                .durationSeconds(duration)
                .startedAt(Instant.now())
                .active(true)
                .build();

        return toDto(syncEventRepository.save(event), Instant.now());
    }

    @Override
    @Transactional
    public SyncStateDto currentSync() {
        Instant now = Instant.now();
        return syncEventRepository.findFirstByActiveTrueOrderByStartedAtDesc()
                .map(event -> expireIfNeeded(event, now))
                .orElseGet(() -> inactive());
    }

    private SyncStateDto expireIfNeeded(SyncEvent event, Instant now) {
        Instant endsAt = event.getStartedAt().plusSeconds(event.getDurationSeconds());
        if (!now.isBefore(endsAt)) {
            event.setActive(false);
            syncEventRepository.save(event);
            return inactive();
        }
        return toDto(event, now);
    }

    private SyncStateDto toDto(SyncEvent event, Instant now) {
        Instant endsAt = event.getStartedAt().plusSeconds(event.getDurationSeconds());
        long remaining = Math.max(0, Duration.between(now, endsAt).getSeconds());
        MediaItem media = event.getMediaItem();
        return SyncStateDto.builder()
                .active(true)
                .mediaId(media.getId())
                .mediaCode(media.getCode())
                .mediaName(media.getName())
                .mediaType(media.getMediaType())
                .mediaUrl(media.getMediaUrl())
                .durationSeconds(event.getDurationSeconds())
                .startedAt(event.getStartedAt())
                .endsAt(endsAt)
                .remainingSeconds(remaining)
                .build();
    }

    private SyncStateDto inactive() {
        return SyncStateDto.builder().active(false).build();
    }
}
