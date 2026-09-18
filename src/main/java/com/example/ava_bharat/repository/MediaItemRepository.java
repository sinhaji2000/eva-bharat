package com.example.ava_bharat.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.ava_bharat.entity.MediaItem;

public interface MediaItemRepository extends JpaRepository<MediaItem, Long> {

    Optional<MediaItem> findByCode(String code);

    boolean existsByCode(String code);
}
