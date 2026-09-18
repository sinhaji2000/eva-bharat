package com.example.ava_bharat.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.ava_bharat.entity.PlaylistItem;

public interface PlaylistItemRepository extends JpaRepository<PlaylistItem, Long> {

    List<PlaylistItem> findByWindowIdOrderByPositionAsc(Long windowId);

    Optional<PlaylistItem> findTopByWindowIdOrderByPositionDesc(Long windowId);
}
