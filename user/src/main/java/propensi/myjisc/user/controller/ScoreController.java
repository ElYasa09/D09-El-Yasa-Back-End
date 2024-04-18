package propensi.myjisc.user.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import propensi.myjisc.user.dto.ScoreDTO;
import propensi.myjisc.user.model.Score;
import propensi.myjisc.user.model.User;
import propensi.myjisc.user.repository.UserRepository;
import propensi.myjisc.user.service.ScoreService;
import propensi.myjisc.user.service.UserService;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/score")
public class ScoreController {
    @Autowired
    private ScoreService scoreService;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/input/{id}")
    public ResponseEntity<Score> inputNilai(@RequestBody ScoreDTO nilaiDTO, @PathVariable Long id) {
        User user = getUserById(id);
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Score nilai = scoreService.simpanNilai(nilaiDTO, user);
        return new ResponseEntity<>(nilai, HttpStatus.CREATED);
    }

    public User getUserById(Long id) {
        Optional<User> userOptional = userRepository.findById(id);
        return userOptional.orElseThrow(() -> new RuntimeException("User not found"));
    }

    @GetMapping("/scores/{userId}")
    public ResponseEntity<List<ScoreDTO>> getUserScores(@PathVariable Long userId) {
        List<ScoreDTO> scores = scoreService.getScoresByUserId(userId);
        if (scores.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(scores, HttpStatus.OK);
    }

}
