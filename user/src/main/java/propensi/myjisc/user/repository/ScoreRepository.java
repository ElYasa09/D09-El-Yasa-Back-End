package propensi.myjisc.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import propensi.myjisc.user.model.Score;
import java.util.List;
import java.util.UUID;

public interface ScoreRepository extends JpaRepository<Score, Long> {
    List<Score> findByIdSiswa(Long idSiswa);

    List<Score> findByIdMapel(UUID idMapel);
}
