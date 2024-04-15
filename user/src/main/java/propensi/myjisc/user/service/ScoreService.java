package propensi.myjisc.user.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

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
        score.setTipeNilai(scoreDTO.getTipeNilai());
        score.setListNilai(scoreDTO.getListNilai());
        score.setUser(user);
        return scoreRepository.save(score);
    }

    public List<Score> getScoresByUserId(Long userId) {
        // Implement the logic to retrieve scores by user ID from the repository
        return scoreRepository.findByUserId(userId);
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