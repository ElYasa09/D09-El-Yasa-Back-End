package propensi.myjisc.user.service;

import java.rmi.NoSuchObjectException;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import propensi.myjisc.user.model.Score;
import propensi.myjisc.user.repository.ScoreRepository;

@Service
public class ScoreService {

    @Autowired
    private ScoreRepository scoreRepository;

    public Score simpanNilai(Score score) {
        try {
            scoreRepository.save(score);
            return score;
        } catch (Exception e) {
            throw new DataIntegrityViolationException("Data untuk id Siswa ini telah ada");
        }
    }

    public List<Score> getListScoreByIdMapel(UUID idMapel) throws NoSuchObjectException {
        var listScore = scoreRepository.findByIdMapel(idMapel);

        if (listScore == null || listScore.isEmpty()) {
            throw new NoSuchObjectException("No data found");
        }

        return listScore;
    }

    public List<Score> getListScoreByIdSiswa(Long idSiswa) throws NoSuchObjectException {
        var listScore = scoreRepository.findByIdSiswa(idSiswa);

        if (listScore == null || listScore.isEmpty()) {
            throw new NoSuchObjectException("No data found");
        }

        return listScore;
    }

    public Score findByIdScore(Long idScore) {
        return scoreRepository.findById(idScore).orElse(null);
    }

    public Score updateScore(Score score) throws NoSuchObjectException {
        var existingScore = scoreRepository.findById(score.getIdNilai()).orElse(null);

        if (existingScore == null) {
            throw new NoSuchObjectException("Score not found");
        } else {
            existingScore.setTipeNilai(score.getTipeNilai());
            existingScore.setListNilai(score.getListNilai());
            scoreRepository.save(existingScore);
            return existingScore;
        }
    }

    public void deleteListScoreByIdMapel(UUID idMapel) throws NoSuchObjectException {
        var listScore = scoreRepository.findByIdMapel(idMapel);

        if (listScore.isEmpty()) {
            throw new NoSuchObjectException("No data found");
        }

        scoreRepository.deleteAll(listScore);

    }

    public void deleteScorebyIdScore(Long idScore) throws NoSuchObjectException {
        var existingScore = findByIdScore(idScore);

        if (existingScore == null) {
            throw new NoSuchObjectException("Score not found");
        }

        scoreRepository.delete(existingScore);
    }

}