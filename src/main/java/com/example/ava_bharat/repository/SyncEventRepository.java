package com.example.ava_bharat.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.ava_bharat.entity.SyncEvent;

public interface SyncEventRepository extends JpaRepository<SyncEvent, Long> {

    Optional<SyncEvent> findFirstByActiveTrueOrderByStartedAtDesc();
}
