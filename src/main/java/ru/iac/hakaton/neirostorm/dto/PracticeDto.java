package ru.iac.hakaton.neirostorm.dto;

import lombok.Data;
import ru.iac.hakaton.neirostorm.model.Topic;

import javax.validation.constraints.NotNull;

@Data
public class PracticeDto {
    @NotNull
    private String ownerName;
    @NotNull
    private String title;
    @NotNull
    private String description;
    @NotNull
    private String steps;
    @NotNull
    private String example;
    @NotNull
    private String conclusion;
    private String previewImage;
    @NotNull
    private Topic topic;
}
