package ru.iac.hakaton.neirostorm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.iac.hakaton.neirostorm.model.Vote;

import java.util.List;
import java.util.Optional;

@Repository
public interface VoteRepository extends JpaRepository<Vote, Long> {
    Optional<Vote> findByPracticeIdAndIpAddress(Long practiceId, String ipAddress);

    List<Vote> findAllByPracticeId(Long practiceId);
}
