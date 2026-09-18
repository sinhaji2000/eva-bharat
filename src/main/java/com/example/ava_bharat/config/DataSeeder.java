package com.example.ava_bharat.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.example.ava_bharat.entity.MediaItem;
import com.example.ava_bharat.entity.MediaType;
import com.example.ava_bharat.entity.PlaylistItem;
import com.example.ava_bharat.entity.Window;
import com.example.ava_bharat.repository.MediaItemRepository;
import com.example.ava_bharat.repository.PlaylistItemRepository;
import com.example.ava_bharat.repository.WindowRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {

    private final MediaItemRepository mediaItemRepository;
    private final WindowRepository windowRepository;
    private final PlaylistItemRepository playlistItemRepository;

    @Override
    @Transactional
    public void run(String... args) {
        if (mediaItemRepository.count() > 0) {
            return;
        }

        MediaItem m1 = saveMedia("M1", "Mountain Still", MediaType.IMAGE,
                "https://picsum.photos/id/1015/1280/720", 8);
        MediaItem m2 = saveMedia("M2", "Forest Path", MediaType.IMAGE,
                "https://picsum.photos/id/1018/1280/720", 8);
        MediaItem m3 = saveMedia("M3", "Big Buck Bunny", MediaType.VIDEO,
                "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4", 12);
        MediaItem m4 = saveMedia("M4", "Coastline", MediaType.IMAGE,
                "https://picsum.photos/id/1016/1280/720", 7);
        MediaItem m5 = saveMedia("M5", "Blank", MediaType.BLANK, null, 4);
        MediaItem m6 = saveMedia("M6", "For Bigger Blazes", MediaType.VIDEO,
                "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerBlazes.mp4", 10);

        Window windowA = saveWindow("Window A");
        Window windowB = saveWindow("Window B");
        Window windowC = saveWindow("Window C");

        addItems(windowA, new Object[][] {
                { m1, 8 }, { m2, 8 }, { m3, 12 }
        });
        addItems(windowB, new Object[][] {
                { m4, 7 }, { m2, 8 }, { m5, 4 }, { m1, 8 }
        });
        addItems(windowC, new Object[][] {
                { m3, 12 }, { m6, 10 }, { m2, 8 }
        });
    }

    private MediaItem saveMedia(String code, String name, MediaType type, String url, int duration) {
        return mediaItemRepository.save(MediaItem.builder()
                .code(code)
                .name(name)
                .mediaType(type)
                .mediaUrl(url)
                .durationSeconds(duration)
                .active(true)
                .build());
    }

    private Window saveWindow(String name) {
        return windowRepository.save(Window.builder()
                .name(name)
                .cycleDurationSeconds(Window.FIVE_HOUR_CYCLE_SECONDS)
                .active(true)
                .build());
    }

    private void addItems(Window window, Object[][] rows) {
        int position = 0;
        for (Object[] row : rows) {
            playlistItemRepository.save(PlaylistItem.builder()
                    .window(window)
                    .mediaItem((MediaItem) row[0])
                    .durationSeconds((Integer) row[1])
                    .position(position++)
                    .build());
        }
    }
}
