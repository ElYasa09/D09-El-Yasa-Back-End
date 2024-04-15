package propensi.myjisc.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import propensi.myjisc.user.model.Score;
import propensi.myjisc.user.model.User;

import java.util.List;
import java.util.UUID;

@Repository
public interface ScoreRepository extends JpaRepository<Score, Long> {

    List<Score> findByUserId(Long userId);
    // Score findByUser(User user);
}