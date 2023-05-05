package ru.iac.hakaton.neirostorm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.iac.hakaton.neirostorm.dto.PracticeDto;
import ru.iac.hakaton.neirostorm.model.Practice;
import ru.iac.hakaton.neirostorm.repository.PracticeRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class PracticeService {
    @Autowired
    private PracticeRepository practiceRepository;

    public List<Practice> getPractices() {
        return practiceRepository.findAll();
    }

    public Practice getPracticeById(long id) {
        Optional<Practice> practiceOpt = practiceRepository.findById(id);

        if (!practiceOpt.isPresent()) {
            throw new IllegalArgumentException("Invalid practice id: " + id);
        }

        return practiceOpt.get();
    }

    public Practice addPractice(PracticeDto practiceDto) {
        Practice practice = Practice.builder()
                .topic(practiceDto.getTopic())
                .title(practiceDto.getTitle())
                .description(practiceDto.getDescription())
                .steps(practiceDto.getSteps())
                .example(practiceDto.getExample())
                .conclusion(practiceDto.getConclusion())
                .previewImage(practiceDto.getPreviewImage())
                .createdAt(LocalDateTime.now())
                .build();

        return practiceRepository.save(practice);
    }
}
