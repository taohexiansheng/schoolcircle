package com.xiaoyuan.schoolcircle.controller;

import com.xiaoyuan.schoolcircle.service.OcrService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/ai")
public class OcrController {
    @Autowired
    private OcrService ocrService;

    @PostMapping("/ocr")
    public String recognize(@RequestParam("file") MultipartFile file) {
        return ocrService.recognizeText(file);
    }
}
