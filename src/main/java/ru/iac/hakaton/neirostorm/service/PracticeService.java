package ru.iac.hakaton.neirostorm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ru.iac.hakaton.neirostorm.util.PracticeSpecifications;
import ru.iac.hakaton.neirostorm.model.Practice;
import ru.iac.hakaton.neirostorm.repository.PracticeRepository;


import java.util.List;

@Service
public class PracticeService {
    @Autowired
    private PracticeRepository practiceRepository;

    public List<Practice> searchPractices(String keyword, String topic) {
        Specification<Practice> spec = Specification
                .where(PracticeSpecifications.title(keyword))
                .and(PracticeSpecifications.topicIs(topic));
        return practiceRepository.findAll(spec);
    }
}
