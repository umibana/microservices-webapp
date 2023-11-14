package com.example.scoreservice.Services;

import com.example.scoreservice.Entities.ScoreEntity;
import com.example.scoreservice.Repositories.ScoreRepository;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class ScoreService {
    @Autowired
    ScoreRepository scoreRepository;

    @Autowired
    RestTemplate restTemplate;



    public List<ScoreEntity> getByRut(String userRut){
        return scoreRepository.findByUserRut(userRut);
    }
    public void addGrades(MultipartFile file) {
        try {
            // We read the file
            InputStream inputStream = file.getInputStream();
            // We create a new XLSX object
            Workbook workbook = new XSSFWorkbook(inputStream);
            Sheet sheet = workbook.getSheetAt(0);
            // Formatter to read as string
            DataFormatter formatter = new DataFormatter();
            // We read the file row by row
            // First cell should be RUT, then date, then score
            for (Row row : sheet) {
                String rut = formatter.formatCellValue(row.getCell(0));
                String date = formatter.formatCellValue(row.getCell(1));
                int score = Integer.parseInt(formatter.formatCellValue(row.getCell(2)));
                // We convert the date string to a LocalDate
                LocalDate localDate = LocalDate.parse(date, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
                // Check if the date is already added
                ScoreEntity scoreEntity = scoreRepository.findByUserRutAndDate(rut, localDate);
                    if (scoreEntity == null) {
                        // If the date is not added, create a new ScoreEntity
                        ScoreEntity newScore = new ScoreEntity();
                        newScore.setUserRut(rut);
                        newScore.setDate(localDate);
                        newScore.setScore(score);
                        scoreRepository.save(newScore); // Save the new ScoreEntity
                    }

            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public int getAverageScore(String userRut) {
        List<ScoreEntity> scores = scoreRepository.findByUserRut(userRut);
        int total = 0;
        for (ScoreEntity score : scores) {
            total += score.getScore();
        }
        if (scores.size() == 0 ){
            return 0;
        }
        return total / scores.size();
    }


    public int getExamsTaken(String userRut) {
        List<ScoreEntity> scores = scoreRepository.findByUserRut(userRut);
        return scores.size();

    }
    public Integer getDiscountScore(String userRut) {
        int score = getAverageScore(userRut);
        if (score >= 950) {
            return 10;
        } else if (score >= 900) {
            return 5;
        } else if (score >= 850) {
            return 2;
        }
        return 0;

    }


}
