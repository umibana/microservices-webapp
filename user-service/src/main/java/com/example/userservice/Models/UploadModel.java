package com.example.userservice.Models;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class UploadModel {

    private MultipartFile file;
}
