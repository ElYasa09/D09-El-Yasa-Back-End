package propensi.myjisc.user.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import propensi.myjisc.user.dto.CreateScoreDTO;
import propensi.myjisc.user.dto.ScoreMapper;
import propensi.myjisc.user.dto.UpdateScoreDTO;
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
import org.springframework.dao.DataIntegrityViolationException;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/score")
public class ScoreController {
    @Autowired
    private ScoreService scoreService;

    @Autowired
    ScoreMapper scoreMapper;

    @PostMapping("/input")
    public ResponseEntity<?> inputNilai(@Valid @RequestBody CreateScoreDTO createScoreDTO,
            BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            Map<String, Object> responseBody = new HashMap<>();
            responseBody.put("status", "fail");

            responseBody.put("data", "Something went wrong!");

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseBody);
        }

        try {
            var scoreFromDTO = scoreMapper.createScoreDTOToScore(createScoreDTO);
            var score = scoreService.simpanNilai(scoreFromDTO);

            Map<String, Object> responseBody = new HashMap<>();
            responseBody.put("status", "success");
            responseBody.put("data", score);

            return ResponseEntity.status(HttpStatus.OK).body(responseBody);
        } catch (DataIntegrityViolationException e) {
            Map<String, Object> responseBody = new HashMap<>();
            responseBody.put("status", "fail");
            responseBody.put("message", "Data for this student ID already exists");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseBody);
        } catch (Exception e) {
            Map<String, Object> responseBody = new HashMap<>();
            responseBody.put("message", "Something went wrong");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseBody);
        }
    }

    @GetMapping("/view-all/mapel/{idMapel}")
    public ResponseEntity<?> listNilaiByIdMapel(@PathVariable("idMapel") String idMapel) throws NoSuchObjectException {

        List<Score> listScoreByIdMapel = scoreService.getListScoreByIdMapel(UUID.fromString(idMapel));

        if (listScoreByIdMapel.isEmpty()) {
            Map<String, Object> responseBody = new HashMap<>();
            responseBody.put("message", "Data not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseBody);
        }

        try {
            Map<String, Object> responseBody = new HashMap<>();
            responseBody.put("status", "success");
            responseBody.put("data", listScoreByIdMapel);

            return ResponseEntity.status(HttpStatus.OK).body(responseBody);
        } catch (Exception e) {
            Map<String, Object> responseBody = new HashMap<>();
            responseBody.put("message", "Something went wrong");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseBody);
        }
    }

    @GetMapping("/view-all/siswa/{idSiswa}")
    public ResponseEntity<?> listNilaiByIdSiswa(@PathVariable("idSiswa") Long idSiswa) throws NoSuchObjectException {

        List<Score> listScoreByIdMapel = scoreService.getListScoreByIdSiswa(idSiswa);

        if (listScoreByIdMapel.isEmpty()) {
            Map<String, Object> responseBody = new HashMap<>();
            responseBody.put("message", "Data not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseBody);
        }

        try {
            Map<String, Object> responseBody = new HashMap<>();
            responseBody.put("status", "success");
            responseBody.put("data", listScoreByIdMapel);

            return ResponseEntity.status(HttpStatus.OK).body(responseBody);
        } catch (Exception e) {
            Map<String, Object> responseBody = new HashMap<>();
            responseBody.put("message", "Something went wrong");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseBody);
        }
    }

    @GetMapping("/{idScore}")
    public ResponseEntity<?> scoreByIdScore(@PathVariable("idScore") Long idScore) {
        Score scoreByIdScore = scoreService.findByIdScore(idScore);

        if (scoreByIdScore == null) {
            Map<String, Object> responseBody = new HashMap<>();
            responseBody.put("message", "Data not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseBody);
        }

        try {
            Map<String, Object> responseBody = new HashMap<>();
            responseBody.put("status", "success");
            responseBody.put("data", scoreByIdScore);

            return ResponseEntity.status(HttpStatus.OK).body(responseBody);
        } catch (Exception e) {
            Map<String, Object> responseBody = new HashMap<>();
            responseBody.put("message", "Something went wrong");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseBody);
        }
    }

    @PutMapping("/update/{idScore}")
    public ResponseEntity<?> updateScore(@PathVariable("idScore") Long idScore,
            @Valid @RequestBody UpdateScoreDTO updateScoreDTO,
            BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            Map<String, Object> responseBody = new HashMap<>();
            responseBody.put("status", "fail");

            responseBody.put("data", "invalid data");

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseBody);
        }

        if (scoreService.findByIdScore(idScore) == null) {
            Map<String, Object> responseBody = new HashMap<>();
            responseBody.put("message", "Score not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseBody);
        }

        try {
            var scoreFromDTO = scoreMapper.updateScoreDTOToScore(updateScoreDTO);
            scoreFromDTO.setIdNilai(idScore);
            var score = scoreService.updateScore(scoreFromDTO);

            Map<String, Object> responseBody = new HashMap<>();
            responseBody.put("status", "success");
            responseBody.put("data", score);

            return ResponseEntity.status(HttpStatus.OK).body(responseBody);
        } catch (Exception e) {
            Map<String, Object> responseBody = new HashMap<>();
            responseBody.put("message", "Something went wrong");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseBody);
        }
    }

    @DeleteMapping("/delete/mapel/{idMapel}")
    public ResponseEntity<?> deleteListScoreMapel(@PathVariable("idMapel") String idMapel) {
        try {
            var listScore = scoreService.getListScoreByIdMapel(UUID.fromString(idMapel));

            if (listScore == null || listScore.isEmpty()) {
                Map<String, Object> responseBody = new HashMap<>();
                responseBody.put("message", "Data not found");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseBody);
            }

            scoreService.deleteListScoreByIdMapel(UUID.fromString(idMapel));

            Map<String, Object> responseBody = new HashMap<>();
            responseBody.put("status", "success");
            responseBody.put("data", "List Score has been deleted");

            return ResponseEntity.status(HttpStatus.OK).body(responseBody);
        } catch (Exception e) {
            Map<String, Object> responseBody = new HashMap<>();
            responseBody.put("message", "Something went wrong");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseBody);
        }
    }

    @DeleteMapping("/delete/{idScore}")
    public ResponseEntity<?> deleteScoreByIdScore(@PathVariable("idScore") Long idScore) {
        try {
            var score = scoreService.findByIdScore(idScore);

            if (score == null) {
                Map<String, Object> responseBody = new HashMap<>();
                responseBody.put("message", "Data not found");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseBody);
            }

            scoreService.deleteScorebyIdScore(idScore);

            Map<String, Object> responseBody = new HashMap<>();
            responseBody.put("status", "success");
            responseBody.put("data", "Score has been deleted");

            return ResponseEntity.status(HttpStatus.OK).body(responseBody);
        } catch (Exception e) {
            Map<String, Object> responseBody = new HashMap<>();
            responseBody.put("message", "Something went wrong");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseBody);
        }
    }

}
