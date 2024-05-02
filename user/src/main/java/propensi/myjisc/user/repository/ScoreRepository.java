package propensi.myjisc.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import propensi.myjisc.user.model.Score;

import java.util.UUID;

public interface ScoreRepository extends JpaRepository<Score, UUID> {

    Score findByUserId(Long userId);
    // Score findByUser(User user);
}