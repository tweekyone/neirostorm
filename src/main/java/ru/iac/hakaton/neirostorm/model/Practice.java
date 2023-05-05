package ru.iac.hakaton.neirostorm.model;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
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

    @Column(name = "owner_name", columnDefinition = "LONGVARCHAR")
    private String ownerName;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "views", nullable = false)
    private int views;

    public float getTotalRating() {
        int likes = (int) votes.stream().filter(vote -> vote.getVoteValue() == 1).count();
        int dislikes = (int) votes.stream().filter(vote -> vote.getVoteValue() == -1).count();

        if (likes + dislikes == 0) {
            // обработка случая, когда нет лайков и дизлайков
            return 0;
        } else {
            float rating = (float) (likes * 1 + dislikes * -1) / (likes + dislikes);
            // обработка случая, когда есть лайки и дизлайки
            return rating;
        }
    }


    @OneToMany(mappedBy = "practice")
    private List<Vote> votes;
}
