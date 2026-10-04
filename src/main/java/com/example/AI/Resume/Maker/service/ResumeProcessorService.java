package com.example.AI.Resume.Maker.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.AI.Resume.Maker.dto.ResumeProfile;
import com.example.AI.Resume.Maker.exception.InvalidResumeException;

@Service
public class ResumeProcessorService {

    private final PdfService pdfService;
    private final ResumeParser resumeParser;

    public ResumeProcessorService(
            PdfService pdfService,
            ResumeParser resumeParser) {

        this.pdfService = pdfService;
        this.resumeParser = resumeParser;
    }

    public String extractText(MultipartFile file)
            throws Exception {

        validateFile(file);

        return pdfService.extractText(file);
    }

    public ResumeProfile process(MultipartFile file)
            throws Exception {

        validateFile(file);

        String resumeText =
                pdfService.extractText(file);

        return resumeParser.parse(resumeText);
    }

    public ResumeProfile processText(String resumeText) {

        if (resumeText == null || resumeText.isBlank()) {
            throw new InvalidResumeException(
                    "Resume does not contain readable text."
            );
        }

        return resumeParser.parse(resumeText);
    }

    private void validateFile(MultipartFile file) {

        if (file == null || file.isEmpty()) {
            throw new InvalidResumeException(
                    "Please upload a resume file."
            );
        }

        String fileName =
                file.getOriginalFilename();

        if (fileName == null
                || !fileName.toLowerCase().endsWith(".pdf")) {

            throw new InvalidResumeException(
                    "Only PDF resume files are supported."
            );
        }
    }
}