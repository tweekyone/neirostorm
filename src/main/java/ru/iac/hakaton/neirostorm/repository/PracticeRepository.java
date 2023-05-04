package ru.iac.hakaton.neirostorm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.iac.hakaton.neirostorm.model.Practice;

import java.util.List;

@Repository
public interface PracticeRepository extends JpaRepository<Practice, Long> {
    List<Practice> findByUserId(Long userId);
}
