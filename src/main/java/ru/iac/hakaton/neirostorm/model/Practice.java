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

    @Column(nullable = false, columnDefinition = "LONGVARCHAR")
    private String title;

    @Column(nullable = false, columnDefinition = "LONGVARCHAR")
    private String description;

    @Column(columnDefinition = "LONGVARCHAR")
    private String steps;

    @Column(columnDefinition = "LONGVARCHAR")
    private String example;

    @Column(columnDefinition = "LONGVARCHAR")
    private String conclusion;

    @Column(length = 50)
    private String topic;

    @Column(name = "preview_image", columnDefinition = "LONGVARCHAR")
    private String previewImage;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;
}
