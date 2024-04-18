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

import java.lang.annotation.Repeatable;
import java.rmi.NoSuchObjectException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


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
        return new ResponseEntity<>(nilai, HttpStatus.OK);
    }

    public User getUserById(Long id) {
        Optional<User> userOptional = userRepository.findById(id);
        return userOptional.orElseThrow(() -> new RuntimeException("User not found"));
    }

    @GetMapping("/scores/{userId}")
    public ResponseEntity<?> getUserScores(@PathVariable Long userId) throws NoSuchObjectException {
        Score scores = scoreService.getScoresByUserId(userId);
        if (scores == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }


        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("message", "success");
        responseBody.put("data", scores);

        return ResponseEntity.ok().body(responseBody);
    }

    @PutMapping("/score/update/{id}")
    public ResponseEntity<?> updateScore(@PathVariable String id, @RequestBody ScoreDTO newScoreData) {
        try {
            scoreService.updateScore(UUID.fromString(id), newScoreData);
            Map<String, Object> responseBody = new HashMap<>();
            Score scores = scoreService.getScoreByIdScore(UUID.fromString(id));
            responseBody.put("message", "success");
            responseBody.put("data", scores);

        return ResponseEntity.ok().body(responseBody);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    

    @DeleteMapping("/score/{idScore}")
    public ResponseEntity<?> deleteScore(@PathVariable String idScore) {
        try {
            scoreService.deleteScore(UUID.fromString(idScore));
            Map<String, Object> responseBody = new HashMap<>();
            responseBody.put("status", "success");
            responseBody.put("data", "Score has been deleted");

            return ResponseEntity.status(HttpStatus.OK).body(responseBody);
        } catch (Exception e) {
            Map<String, Object> responseBody = new HashMap<>();
            responseBody.put("message", "Something went wrong!");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseBody);
        }
    } 
}
