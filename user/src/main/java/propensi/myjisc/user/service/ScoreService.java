package propensi.myjisc.user.service;

import java.rmi.NoSuchObjectException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import javax.management.RuntimeErrorException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import propensi.myjisc.user.controller.UserController;
import propensi.myjisc.user.dto.ScoreDTO;
import propensi.myjisc.user.model.Score;
import propensi.myjisc.user.model.User;
import propensi.myjisc.user.repository.ScoreRepository;

@Service
public class ScoreService {
    
    @Autowired
    private ScoreRepository scoreRepository;

    public Score simpanNilai(ScoreDTO scoreDTO, User user) {

        Score score = new Score();
        score.setIdNilai(scoreDTO.getIdNilai());
        score.setTipeNilai(scoreDTO.getTipeNilai());
        score.setListNilai(scoreDTO.getListNilai());
        score.setIdMapel(scoreDTO.getIdMapel());
        score.setUser(user);
        return scoreRepository.save(score);
    }

    // public List<Score> getScoresByUserId(Long userId) {
    //     // Implement the logic to retrieve scores by user ID from the repository
    //     return scoreRepository.findByUserId(userId);
    // }

    public Score getScoresByUserId(Long userId) throws NoSuchObjectException {
        Score scores = scoreRepository.findByUserId(userId);
    
        if (scores == null) {
            throw new NoSuchObjectException("No Scores Found for this ids");
        }
    
        return scores;
    }
    

    private ScoreDTO mapToScoreResponseDTO(Score score) {
        ScoreDTO dto = new ScoreDTO();
        dto.setIdNilai(score.getIdNilai());
        dto.setTipeNilai(score.getTipeNilai());
        dto.setListNilai(score.getListNilai());
        return dto;
    }

    public ScoreDTO updateScore(UUID id, ScoreDTO newScoreData) {
        Score score = scoreRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Score not found"));
    
        score.setTipeNilai(newScoreData.getTipeNilai());
        score.setListNilai(newScoreData.getListNilai());
    
        Score updatedScore = scoreRepository.save(score);
    
        return mapToScoreResponseDTO(updatedScore);
    }

    public void deleteScore(UUID idScore) {
        Score score = scoreRepository.findById(idScore)
            .orElseThrow(() -> new RuntimeException("Score not found"));

        scoreRepository.delete(score);
    }

    public Score getScoreByIdScore(UUID idScore) {
        Score score = scoreRepository.findById(idScore)
            .orElseThrow(() -> new RuntimeException("Score not found"));

        return score;
    }



    // @Autowired
    // public ScoreService(ScoreRepository scoreRepository, UserService userService) {
    //     this.scoreRepository = scoreRepository;
    //     this.userService = userService;
    // }

    // public void inputScore(Long userId, String type, Long value) {
    //     // Mengecek apakah user dengan ID yang diberikan ada
    //     User user = userService.getUserById(userId);
    //     if (user == null) {
    //         throw new IllegalArgumentException("User not found");
    //     }
        
    //     // Mencari score yang sesuai untuk user dengan ID yang diberikan
    //     Score score = scoreRepository.findByUser(user);
    //     if (score == null) {
    //         // Jika belum ada score untuk user tersebut, maka buat score baru
    //         score = new Score();
    //         score.setUser(user);
    //     }
        
    //     // Memperbarui nilai sesuai dengan jenis nilai (type)
    //     List<String> tipeNilai = score.getTipeNilai();
    //     List<Long> listNilai = score.getListNilai();
    //     int index = tipeNilai.indexOf(type);
    //     if (index != -1) {
    //         // Jika jenis nilai ditemukan, maka perbarui nilai di indeks yang sesuai
    //         listNilai.set(index, value);
    //     } else {
    //         // Jika jenis nilai tidak ditemukan, tambahkan jenis nilai baru dan nilai yang sesuai
    //         tipeNilai.add(type);
    //         listNilai.add(value);
    //     }
        
    //     // Simpan perubahan score ke dalam database
    //     scoreRepository.save(score);
    // }
}