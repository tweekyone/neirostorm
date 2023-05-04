package ru.iac.hakaton.neirostorm.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Entity
@Table(name = "practices")
public class Practice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 255)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String steps;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String example;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String conclusion;

    @Column(nullable = false, length = 50)
    @Enumerated(EnumType.STRING)
    private Topic topic;

    @Column(name = "preview_image", columnDefinition = "TEXT")
    private String previewImage;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;
}
