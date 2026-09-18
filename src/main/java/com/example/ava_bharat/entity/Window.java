package com.example.ava_bharat.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "windows")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Window {

    public static final int FIVE_HOUR_CYCLE_SECONDS = 18_000;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @Builder.Default
    @Column(name = "cycle_duration_seconds", nullable = false)
    private Integer cycleDurationSeconds = FIVE_HOUR_CYCLE_SECONDS;

    @Builder.Default
    @Column(name = "is_active", nullable = false)
    private Boolean active = true;

    @Builder.Default
    @OneToMany(mappedBy = "window", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("position ASC")
    private List<PlaylistItem> playlistItems = new ArrayList<>();

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
