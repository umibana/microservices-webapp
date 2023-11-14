package com.example.scoreservice.Controllers;

import com.example.scoreservice.Entities.ScoreEntity;
import com.example.scoreservice.Services.ScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@RestController
@RequestMapping("/score")
public class ScoreController {
    @Autowired
    ScoreService scoreService;

    @GetMapping("/hello")
    public ResponseEntity<String> getHelloWorld() {
        return ResponseEntity.ok("Hello World");
    }

    @GetMapping("/{rut}")
    public ResponseEntity<List<ScoreEntity>> getByRut(@PathVariable("rut") String rut) {
        List<ScoreEntity> scores = scoreService.getByRut(rut);
        if (scores == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(scores);

    }

    @GetMapping("/exams/{rut}")
    public ResponseEntity<Integer> getExamsTaken(@PathVariable("rut") String userRut){
        return ResponseEntity.ok(scoreService.getExamsTaken(userRut));

    }

    @GetMapping("/average/{rut}")
    public ResponseEntity<Integer> getAverage(@PathVariable("rut") String userRut){
        return ResponseEntity.ok(scoreService.getAverageScore(userRut));

    }

    @GetMapping("/discount/{rut}")
    public ResponseEntity<Integer> getScoreDiscount(@PathVariable("rut") String userRut){
        return ResponseEntity.ok(scoreService.getDiscountScore(userRut));

    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadGradesFile(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("No file provided");
        }

        try {
            scoreService.addGrades(file); // Assuming scoreService is the service that has the addGrades method
            return ResponseEntity.ok("Grades successfully uploaded and processed.");
        } catch (Exception e) {
            // Log the exception details as well for debugging
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while processing the file.");
        }
    }



}
